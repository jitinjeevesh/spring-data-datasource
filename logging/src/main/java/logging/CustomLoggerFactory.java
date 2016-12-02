package logging;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CustomLoggerFactory implements ILoggerFactory {

    Map<String, Logger> loggerMap;

    public CustomLoggerFactory() {
        loggerMap = new HashMap<String, Logger>();
    }

    public Logger getLogger(String name) {
        Logger frameLogger = loggerMap.get(name);
        if (frameLogger != null) {
            return frameLogger;
        } else {
            Logger newInstance = new CustomLoggerAdapter(name);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
