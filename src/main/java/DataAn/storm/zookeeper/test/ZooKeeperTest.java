package DataAn.storm.zookeeper.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.storm.utils.Utils;

import DataAn.storm.zookeeper.ZooKeeperClient;
import DataAn.storm.zookeeper.ZooKeeperClient.Node;
import DataAn.storm.zookeeper.ZooKeeperClient.ZookeeperExecutor;
import DataAn.storm.zookeeper.ZooKeeperNameKeys;

public class ZooKeeperTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		Map conf=new HashMap<>();
		ZooKeeperNameKeys.setZooKeeperServer(conf, "nim1.storm.com:2182,nim2.storm.com");
		ZooKeeperNameKeys.setNamespace(conf, "test-a");
		ZookeeperExecutor executor=new ZooKeeperClient()
		.connectString(ZooKeeperNameKeys.getZooKeeperServer(conf))
		.namespace(ZooKeeperNameKeys.getNamespace(conf))
		.build();
		
		executor.createPath("/sssaagg", "a-b-v".getBytes("utf-8"));
		
		executor.watchPath("/sssaagg", new ZooKeeperClient.NodeCallback() {
			
			@Override
			public void call(Node node) {
				System.out.println("dd");
			}
		});
		
		Utils.sleep(1000000);
	}
	
	
}
