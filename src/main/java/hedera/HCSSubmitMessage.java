package hedera;

// Hedera SDK v2.56.1
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.TopicId;
import com.hedera.hashgraph.sdk.TopicMessageSubmitTransaction;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.Status;
import com.google.protobuf.ByteString;

public class HCSSubmitMessage {
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
      TopicMessageSubmitTransaction txTopicMessageSubmit = new TopicMessageSubmitTransaction()
          .setTopicId(TopicId.fromString("0.0.6617930")) // *** Fill in the topic ID ***
          .setMessage("hello, HCS! ");

      //Sign the transaction with the client operator private key and submit to a Hedera network
      TransactionResponse txTopicMessageSubmitResponse = txTopicMessageSubmit.execute(client);

      //Request the receipt of the transaction
      TransactionReceipt receiptTopicMessageSubmitTx = txTopicMessageSubmitResponse.getReceipt(client);

      //Get the transaction consensus status
      Status statusTopicMessageSubmitTx = receiptTopicMessageSubmitTx.status;

      //Get the transaction message
      ByteString getTopicMessage = txTopicMessageSubmit.getMessage();

      //Get the transaction ID
      String txTopicMessageSubmitId = txTopicMessageSubmitResponse.transactionId.toString();

      System.out.println("-------------------------------- Submit Message -------------------------------- ");
      System.out.println("Receipt status           : " + statusTopicMessageSubmitTx.toString());
      System.out.println("Transaction ID           : " + txTopicMessageSubmitId);
      System.out.println("Hashscan URL             : " + "https://hashscan.io/testnet/tx/" + txTopicMessageSubmitId);
      System.out.println("Topic Message            : " + getTopicMessage.toStringUtf8());

    } catch (Exception e) {
      System.err.println(e);
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }
}