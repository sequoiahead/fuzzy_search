package com.agileengine.htmlnlz;

import com.agileengine.htmlnlz.index.Index;
import com.agileengine.htmlnlz.index.Indexer;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Application {

    private static Logger LOGGER = Logger.getLogger(Application.class);

    public static void main(String[] argv) throws Exception {
        Application app = new Application(argv);
        Optional<Element> original;
        original = app.findOriginalElement();
        if (!original.isPresent()) {
            throw new IllegalStateException(String.format("Element with id=%s not found in original file", app.idOriginalElem));
        }
        Index index = app.index(original.get());
        Optional<String> elem = null;
        try {
             elem = app.findClosest(index);
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to parse %s", app.fileOther.getCanonicalPath()));
        }
        if (!elem.isPresent()) {
            LOGGER.info(String.format("Original element has not been found in %s", app.fileOther.getCanonicalPath()));
            return;
        }
        LOGGER.info(String.format("Xpath is: %s", elem.get()));
    }

    private static final String ID_ORIGINAL_ELEM_DEFAULT = "make-everything-ok-button";
    private static final String CHARSET_DEFAULT = "utf8";
    private final String idOriginalElem;
    private final String charset;
    private final String jarFileName;

    private final File fileOriginal;
    private final File fileOther;

    private Indexer indexer;
    private ElementMatcher matcher;

    public Application(String[] argv) throws IOException {
        Properties appProps = new Properties();
        appProps.load(Application.class.getClassLoader()
                .getResourceAsStream("com/agileengine/htmlnlz/application.properties"));

        jarFileName = appProps.getProperty("jar.name");
        charset = appProps.getProperty("charset", CHARSET_DEFAULT);
        idOriginalElem = appProps.getProperty("elem.id", ID_ORIGINAL_ELEM_DEFAULT);

        if (argv.length < 2) {
            throw new IllegalArgumentException(usageString());
        }
        fileOriginal = new File(argv[0]);
        fileOther = new File(argv[1]);
        if (!fileOriginal.exists() || !fileOriginal.canRead()) {
            throw new IllegalArgumentException(String.format("Original file %s does not exist or not readable", fileOriginal.getAbsolutePath()));
        }
        if (!fileOther.exists() || !fileOther.canRead()) {
            throw new IllegalArgumentException(String.format("Other file %s does not exist or not readable", fileOther.getAbsolutePath()));
        }
        indexer = new Indexer();
        matcher = new ElementMatcher();
    }

    private Optional<String> findClosest(Index index) throws IOException {
        Document doc = Jsoup.parse(fileOther, charset);
        return matcher.findElement(index, doc);
    }

    private Index index(Element element) {
        return indexer.index(element);
    }

    private Optional<Element> findOriginalElement() throws IOException {
        Document doc = Jsoup.parse(fileOriginal, charset);
        return Optional.of(doc.getElementById(idOriginalElem));
    }

    private String usageString() {
        return String.format("Usage: java -jar %s <originFilePath> <otherFilePath>",
                jarFileName);
    }

}
