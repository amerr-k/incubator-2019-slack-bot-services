package com.welcome.bot.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.mockito.internal.matchers.Find;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Poll;
import com.welcome.bot.domain.PollResult;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.exception.base.BaseException;
import com.welcome.bot.models.ChoiceDTO;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.PollCreateDTO;
import com.welcome.bot.models.PollDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.repository.ChoiceRepository;
import com.welcome.bot.repository.PollRepository;
import com.welcome.bot.repository.PollResultsRepository;
import com.welcome.bot.slack.api.SlackClientApi;

@Service
@Transactional
public class PollService {
	
	PollRepository pollRepository;
	ModelMapper modelMapper;
	ChoiceService choiceService;
	ChoiceRepository choiceRepository;
	PollResultsRepository pollResultsRepository;
	SlackClientApi slackClientApi;
	ChannelService channelService;
	SlackService slackService;
	
	@Autowired
	public PollService(PollRepository pollRepository, 
			ModelMapper modelMapper, 
			ChoiceService choiceService,
			ChoiceRepository choiceRepository,
			PollResultsRepository pollResultsRepository,
			SlackClientApi slackClientApi, 
			ChannelService channelService, 
			SlackService slackService) {
		this.pollRepository = pollRepository;
		this.modelMapper = modelMapper;
		this.choiceService = choiceService;
		this.choiceRepository = choiceRepository;
		this.pollResultsRepository = pollResultsRepository;
		this.slackClientApi = slackClientApi;
		this.channelService = channelService;
		this.slackService = slackService;
	}
	
	//@Transactional(propagation = Propagation.MANDATORY )
	public PollDTO createPoll(PollCreateDTO pollModel) {
		String channel = channelService.getChannelById(pollModel.getChannel());
		
		Poll poll = new Poll(pollModel.getTitle(), channel, pollModel.isActive(), pollModel.getActiveUntil());
		pollRepository.save(poll);
		
		List<Choice> choiceList = modelMapper.map(pollModel.getChoiceList(), new TypeToken<List<Choice>>(){}.getType());

		choiceService.createChoices(poll, choiceList);
		
		if(poll.isActive()) {
			slackService.createPoll(poll, choiceList, pollModel.getChannel());
		}
		
		PollDTO pollDTO = convertToDto(poll, choiceList);
		return pollDTO;
	}

	public Page<PollDTO> getAllPolls(Pageable pageable) {
		Page<Poll> pollPage = pollRepository.findAll(pageable);
		
		List<Poll> pollList = pollPage.getContent();
		
		List<PollDTO> pollDtoList = new ArrayList<>();
		for (Poll poll : pollList) {
			
			List<Choice> choiceList = choiceRepository.findByPoll(poll);
			
			//mockup votes
			//mockupVotes(choiceList);
			
			PollDTO pollDTO = convertToDto(poll, choiceList);
			pollDtoList.add(pollDTO);
		}
		
		Page<PollDTO> pollDtoPage = new PageImpl<PollDTO>(pollDtoList, pageable, pollPage.getTotalElements());
		
		return pollDtoPage;
	}

	private PollDTO convertToDto(Poll poll, List<Choice> choiceList) {
		PollDTO pollDTO = modelMapper.map(poll, PollDTO.class);
		List<ChoiceDTO> choiceDTOs = choiceService.convertToChoiceDTOs(choiceList);
		pollDTO.setChoiceList(choiceDTOs);
		return pollDTO;
	}
	
	public PollDTO getPoll(Pageable pageable, Integer pollId) {
		Poll poll = pollRepository.findById(pollId).orElseThrow();
		List<Choice> choiceList = choiceRepository.findByPoll(poll); 
		PollDTO pollDTO = convertToDto(poll, choiceList);
		return pollDTO;
	}
	
	public void mockupVotes(List<Choice> choiceList) {
		//mockup votes
	    Random rand = new Random();
	    UUID pollUUID = choiceList.get(0).getPoll().getPollUuid();
	    int numberOfElements = 25;
	    for (int i = 0; i < numberOfElements; i++) {
	        int randomIndex = rand.nextInt(choiceList.size());
	        Choice randomElement = choiceList.get(randomIndex);
	        PollResult pollResult = new PollResult(null, randomElement.getChoiceId(), pollUUID);
	        pollResultsRepository.save(pollResult);
	    }
	}

	public ResponseEntity<Object> deletePoll(Pageable pageable, Integer pollId) {
		Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new BaseException("Poll not found"));
		
		softDelete(poll);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	private void softDelete(Poll poll) {
		poll.setDeleted();
		poll.setActive(false);
		pollRepository.save(poll);
	}
	
	public List<Poll> getActivePolls(){
		Date date = new Date();
		List<Poll> list = pollRepository.findAllByActiveUntilLessThan(date);
		return list;
	}
	
	
}
