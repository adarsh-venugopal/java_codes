package hedera;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TopicCreateTransaction;

public class HCSTopicCreate {
  public static void main(String[] args) throws Exception {
    Client client = null;
    try {
      // Your account ID and private key from string value
      AccountId MY_ACCOUNT_ID = AccountId.fromString("0.0.1518");
      PrivateKey MY_PRIVATE_KEY = PrivateKey.fromStringED25519("302e020100300506032b657004220420cd131a3981e899537c09cd846547516aeaac1dfe886de1679b7bf38d227831e1");

      // Pre-configured client for test network (testnet)
      client = Client.forTestnet();

      // Set the operator with the account ID and private key
      client.setOperator(MY_ACCOUNT_ID, MY_PRIVATE_KEY);

      //Create the transaction
      TopicCreateTransaction txCreateTopic = new TopicCreateTransaction();

      //Sign with the client operator private key and submit the transaction to a Hedera network
      TransactionResponse txCreateTopicResponse = txCreateTopic.execute(client);

      //Request the receipt of the transaction
      TransactionReceipt receiptCreateTopicTx = txCreateTopicResponse.getReceipt(client);

      //Get the transaction consensus status
      Status statusCreateTopicTx = receiptCreateTopicTx.status;

      //Get the Transaction ID
      String txCreateTopicId = txCreateTopicResponse.transactionId.toString();

      //Get the topic ID
      assert receiptCreateTopicTx.topicId != null;
      String topicId = receiptCreateTopicTx.topicId.toString();

      System.out.println("------------------------------ Create Topic ------------------------------ ");
      System.out.println("Receipt status           : " + statusCreateTopicTx);
      System.out.println("Transaction ID           : " + txCreateTopicId);
      System.out.println("Hashscan URL             : " + "https://hashscan.io/testnet/tx/" + txCreateTopicId);
      System.out.println("Topic ID                 : " + topicId);
    } catch (Exception e) {
      System.err.println(e);
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }
}
