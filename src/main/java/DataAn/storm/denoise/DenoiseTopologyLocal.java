package DataAn.storm.denoise;

import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.spout.RichSpoutBatchExecutor;

import DataAn.storm.StormRunner;
import DataAn.storm.kafka.KafkaNameKeys;
import DataAn.storm.zookeeper.ZooKeeperNameKeys;


public class DenoiseTopologyLocal {

	public static void main(String[] args) throws Exception {
		DenoiseConfig denoiseConfig=new DenoiseConfigParser().parse(args);
		
		StormTopology stormTopology=new DenoiseTopologyBuilder().build(denoiseConfig);
		Config conf=new Config();
		conf.put("storm.flow.worker.id", 1);
		ZooKeeperNameKeys.setZooKeeperServer(conf, "nim1.storm.com:2182,nim2.storm.com");
		ZooKeeperNameKeys.setNamespace(conf, "test-zhongjin");
		KafkaNameKeys.setKafkaServer(conf, "nim2.storm.com:9092");
		conf.put(RichSpoutBatchExecutor.MAX_BATCH_SIZE_CONF, 1);
		conf.setMessageTimeoutSecs(10000);
		int runtimeInSeconds=100000;
		StormRunner.runTopologyLocally(stormTopology, denoiseConfig.getName(), conf, runtimeInSeconds);
		
	}
	
}
