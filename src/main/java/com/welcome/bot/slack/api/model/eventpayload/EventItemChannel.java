package com.welcome.bot.slack.api.model.eventpayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"is_channel",
"name",
"name_normalized",
"created",
"creator",
"is_shared",
"is_org_shared"
})
public class EventItemChannel {

	@JsonProperty("id")
	public String id;
	@JsonProperty("is_channel")
	public Boolean isChannel;
	@JsonProperty("name")
	public String name;
	@JsonProperty("name_normalized")
	public String nameNormalized;
	@JsonProperty("created")
	public Integer created;
	@JsonProperty("creator")
	public String creator;
	@JsonProperty("is_shared")
	public Boolean isShared;
	@JsonProperty("is_org_shared")
	public Boolean isOrgShared;
	
	public EventItemChannel(String id, Boolean isChannel, String name, String nameNormalized, Integer created, String creator, Boolean isShared, Boolean isOrgShared) {
		this.id = id;
		this.isChannel = isChannel;
		this.name = name;
		this.nameNormalized = nameNormalized;
		this.created = created;
		this.creator = creator;
		this.isShared = isShared;
		this.isOrgShared = isOrgShared; 
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Boolean getIsChannel() {
		return isChannel;
	}
	public void setIsChannel(Boolean isChannel) {
		this.isChannel = isChannel;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameNormalized() {
		return nameNormalized;
	}
	public void setNameNormalized(String nameNormalized) {
		this.nameNormalized = nameNormalized;
	}
	
	public Integer getCreated() {
		return created;
	}
	public void setCreated(Integer created) {
		this.created = created;
	}
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Boolean getIsShared() {
		return isShared;
	}
	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}
	
	public Boolean getIsOrgShared() {
		return isOrgShared;
	}
	public void setIsOrgShared(Boolean isOrgShared) {
		this.isOrgShared = isOrgShared;
	}
}
