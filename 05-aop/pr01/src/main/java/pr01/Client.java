package pr01;

import pr01.model.CreditCard;
import pr01.model.Order;
import pr01.purchase.Purchase;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class Client {

  public static void main(String[] args) throws Exception {
    Properties properties = new Properties();
    properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        "org.apache.openejb.client.RemoteInitialContextFactory");
    properties.setProperty(Context.PROVIDER_URL,
        "ejbd://localhost:4201");
    InitialContext ctx = new InitialContext(properties);

    CreditCard card = new CreditCard(12, 2010, "John Doe", "411111111111");
    Order order = new Order(card, new Date(), new ArrayList<>(), new ArrayList<>());


    Purchase purchase = (Purchase)ctx.lookup(
        "java:global/Server/PurchaseBean!pr01.purchase.Purchase");

    boolean status = purchase.processOrder(order);

    System.out.println(status ? "Order processed." : "Order rejected.");
  }
}