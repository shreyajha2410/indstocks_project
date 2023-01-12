package com.workflowengine.indianstocks.utility;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.IOException;

@Named
public class alertmessage implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        var client = Slack.getInstance().methods();

        var logger = LoggerFactory.getLogger("my-awesome-slack-app");

        try {
            // Call the conversations.list method using the built-in WebClient
            logger.info("Get environment details");
            var result = client.conversationsList(r -> r
                    // The token you used to initialize your app
                    .token("xoxb-378830405221-4630989012737-6tXnCBCRYrrFjn8PdEAz5kNC")
            );

            for (Conversation channel : result.getChannels()) {

                if (channel.getName().equals("#indian-stock-broking-workflow-alerts")) {
                    var conversationId = channel.getId();
                    // Print result
                    logger.info("Found conversation ID: {}", conversationId);
                    // Break from for loop
                    break;
                }
            }
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }

        try {
            // Call the chat.postMessage method using the built-in WebClient
            logger.info("Get environment details");
            var result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token("xoxb-378830405221-4630989012737-6tXnCBCRYrrFjn8PdEAz5kNC")
                            .channel("#indian-stock-broking-workflow-alerts")
                            .text("Hello world")

                    // You could also use a blocks[] array to send richer content
            );
            // Print result, which includes information about the message (like TS)
            logger.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }


    }
}






