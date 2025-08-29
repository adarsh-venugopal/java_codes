package hedera;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.SubscriptionHandle;
import com.hedera.hashgraph.sdk.TopicId;
import com.hedera.hashgraph.sdk.TopicMessageQuery;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class HCSTopicSubscribe {

  public static void main(String[] args) throws Exception {

    Client client = null;
    try {
      // Your account ID and private key from string value
      AccountId MY_ACCOUNT_ID = AccountId.fromString("0.0.1518");
      PrivateKey MY_PRIVATE_KEY = PrivateKey.fromStringED25519(
          "302e020100300506032b657004220420cd131a3981e899537c09cd846547516aeaac1dfe886de1679b7bf38d227831e1");

      // Pre-configured client for test network (testnet)
      client = Client.forTestnet();

      // Set the operator with the account ID and private key
      client.setOperator(MY_ACCOUNT_ID, MY_PRIVATE_KEY);

      // Start your code here
      //Create the query
      TopicMessageQuery topicMessageQuery = new TopicMessageQuery()
          .setTopicId(TopicId.fromString("0.0.6617930")); // *** Fill in the topic ID ***

      topicMessageQuery.subscribe(client, topicMessage -> {
            System.out.println(
                "-------------------------------- Get Topic Messages -------------------------------- ");
        // Convert byte array to String using UTF-8 encoding
            String hcsMessage = new String(topicMessage.contents, StandardCharsets.UTF_8);
            System.out.println("Message received is : - " + hcsMessage);
            System.out.println("Message consensus timestamp is : - " + topicMessage.consensusTimestamp);
          });

      while(true);
    } catch (Exception e) {
      System.err.println(e);
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }
}