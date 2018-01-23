package com.orientechnologies.agent.cloud;

import com.orientechnologies.agent.OEnterpriseAgent;
import com.orientechnologies.agent.cloud.processor.server.ListConnectionsCommandProcessor;
import com.orientechnologies.agent.cloud.processor.server.ThreadsDumpCommandProcessor;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.server.distributed.AbstractEnterpriseServerClusterTest;
import com.orientechnologies.orient.server.distributed.ServerRun;
import com.orientechnologies.orientdb.cloud.protocol.Command;
import com.orientechnologies.orientdb.cloud.protocol.CommandResponse;
import com.orientechnologies.orientdb.cloud.protocol.ServerInfo;
import com.orientechnologies.orientdb.cloud.protocol.ServerThreadDump;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Enrico Risa on 19/12/2017.
 */
public class ServerCommandProcessorDistributedTest extends AbstractEnterpriseServerClusterTest {

  private final String DB_NAME = "backupDB";

  @Test
  public void testListConnectionsCommand() throws Exception {

    execute(2, () -> {

      ServerRun firstServer = this.serverInstance.get(0);
      ServerRun secondServer = this.serverInstance.get(1);

      ServerInfo payload = new ServerInfo();
      payload.setName(firstServer.getNodeName());
      Command command = new Command();
      command.setId("test");
      command.setPayload(payload);
      command.setResponseChannel("channelTest");

      ListConnectionsCommandProcessor remove = new ListConnectionsCommandProcessor();

      CommandResponse execute = remove.execute(command, getAgent(secondServer.getNodeName()));

      String result = (String) execute.getPayload();

      Assert.assertNotNull(result);

      ODocument entries = new ODocument().fromJSON(result);
      Collection connections = entries.field("connections");
      Assert.assertTrue(connections.size() > 0);
      return null;
    });

  }

  @Test
  public void testThreadDump() throws Exception {

    execute(2, () -> {

      ServerRun firstServer = this.serverInstance.get(0);
      ServerRun secondServer = this.serverInstance.get(1);

      ServerInfo payload = new ServerInfo();
      payload.setName(firstServer.getNodeName());
      Command command = new Command();
      command.setId("test");
      command.setPayload(payload);
      command.setResponseChannel("channelTest");

      ThreadsDumpCommandProcessor remove = new ThreadsDumpCommandProcessor();

      CommandResponse execute = remove.execute(command, getAgent(secondServer.getNodeName()));

      ServerThreadDump result = (ServerThreadDump) execute.getPayload();

      Assert.assertNotNull(result);
      Assert.assertNotNull(result.getThreadDump());

      return null;
    });

  }

  private OEnterpriseAgent getAgent(String server) {

    return this.serverInstance.stream().filter(serverRun -> serverRun.getNodeName().equals(server)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot find server with name %s", server)))
        .getServerInstance().getPluginByClass(OEnterpriseAgent.class);

  }

  @Override
  protected String getDatabaseName() {
    return DB_NAME;
  }

  @Override
  protected String getDistributedServerConfiguration(ServerRun server) {
    return "orientdb-distributed-server-config-" + server.getServerId() + ".xml";
  }

}