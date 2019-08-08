package com.welcome.bot.slack.api.model.interactionpayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"attachment_id",
"channel_id",
"is_ephemeral",
"message_ts",
"type",
"is_app_unfurl"
})
public class Container {

	@JsonProperty("attachment_id")
	public Integer attachmentId;
	@JsonProperty("channel_id")
	public String channelId;
	@JsonProperty("is_ephemeral")
	public Boolean isEphemeral;
	@JsonProperty("message_ts")
	public String messageTs;
	@JsonProperty("type")
	public String type;
	@JsonProperty("is_app_unfurl")
	public Boolean isAppUnfurl;
	
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Boolean getIsEphemeral() {
		return isEphemeral;
	}
	public void setIsEphemeral(Boolean isEphemeral) {
		this.isEphemeral = isEphemeral;
	}
	public String getMessageTs() {
		return messageTs;
	}
	public void setMessageTs(String messageTs) {
		this.messageTs = messageTs;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getIsAppUnfurl() {
		return isAppUnfurl;
	}
	public void setIsAppUnfurl(Boolean isAppUnfurl) {
		this.isAppUnfurl = isAppUnfurl;
	}
	
}
