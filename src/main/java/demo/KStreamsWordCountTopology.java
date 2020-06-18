package demo;

import io.streamthoughts.azkarra.api.annotations.Component;
import io.streamthoughts.azkarra.api.annotations.TopologyInfo;
import io.streamthoughts.azkarra.api.config.Conf;
import io.streamthoughts.azkarra.api.config.Configurable;
import io.streamthoughts.azkarra.api.streams.TopologyProvider;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;

@Component
@TopologyInfo(description = "A configurable WordCount topology example")
public class KStreamsWordCountTopology implements TopologyProvider, Configurable {

    private String topicSource;
    private String topicSink;
    private String stateStoreName;

    @Override
    public void configure(final Conf conf) {
        topicSource = conf.getOptionalString("topic.source")
                .orElse("streams-plaintext-input");
        topicSink = conf.getOptionalString("topic.sink")
                .orElse("streams-wordcount-output");
        stateStoreName = conf.getOptionalString("state.store.name")
                .orElse("count");
    }

    @Override
    public String version() {
        return Version.getVersion();
    }

    @Override
    public Topology get() {
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, String> source = builder.stream(topicSource);
        source
            .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
            .groupBy((key, value) -> value)
            .count(Materialized.as(stateStoreName))
            .toStream().to(topicSink, Produced.with(Serdes.String(), Serdes.Long()));

        return builder.build();
    }
}