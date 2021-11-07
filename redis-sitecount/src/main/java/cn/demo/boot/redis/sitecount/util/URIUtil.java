package cn.demo.boot.redis.sitecount.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.net.URI;

public class URIUtil {

    public static ImmutablePair</**host*/String, /**uri*/String> foramtUri(String uri) {
        URI u = URI.create(uri);
        String host = u.getHost();
        if (u.getPort() > 0 && u.getPort() != 80) {
            host = host + ":80";
        }

        String baseUri = u.getPath();
        if (u.getFragment() != null) {
            baseUri = baseUri + "#" + u.getFragment();
        }

        if (StringUtils.isNotBlank(baseUri)) {
            baseUri = host + baseUri;
        } else {
            baseUri = host;
        }

        return ImmutablePair.of(host, baseUri);
    }

    public static void main(String[] args) {
        System.out.println(foramtUri("http://10.20.11.216:8080/visit?ip=10.20.11.216"));
        // (10.20.11.216:80,10.20.11.216:80/visit)
    }

}
