package io.breakfastcoders.davinci.utils;


import com.google.testing.compile.JavaFileObjects;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.tools.JavaFileObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilities {
    public static JavaFileObject constructFileObjectForResource(ClassLoader loader, String file, String classPath) throws IOException {
        return JavaFileObjects.forSourceString(classPath, getResourceString(loader, file));
    }


    public static String getResourceString(ClassLoader loader, String file) throws IOException {
        URL url = loader.getResource(file);
        if (url != null) {
            String pendingFile = getFile(url).getAbsolutePath();
            byte[] encoded = Files.readAllBytes(Paths.get(pendingFile));
            return new String(encoded, Charset.defaultCharset());
        } else {
            throw new NullPointerException("Bad resource");
        }
    }

    public static String getUri(ClassLoader loader, String file) throws IOException {
        URL url = loader.getResource(file);
        if (url != null) {
            return getFile(url).getAbsolutePath();
        }
        throw new IOException("Bad resource call");
    }

    private static File getFile(String resourcePath) throws IOException {
        URL url = Thread.currentThread()
                .getClass()
                .getClassLoader()
                .getResource(resourcePath);
        return getFile(url);
    }

    private static File getFile(URL url) throws IOException {
        if (url != null) {
            try {
                return new File(toURI(url));
            } catch (URISyntaxException e) {
                throw new IOException("There was an error converting the url to a URI");
            }
        }
        throw new IOException("URL is null!");
    }

    private static URI toURI(URL url) throws URISyntaxException {
        return new URI(url.toString());
    }

    @Test
    public void shouldReadJson() {
        try {
            assertThat(getResourceString(this.getClass().getClassLoader(), "json/snake/simple_test.json")).isNotNull();
        } catch (IOException e) {
            assertThat(false).isTrue();
        }
    }
}
