package cc.onelooker.kaleido.nfo;

import org.apache.commons.lang3.StringUtils;

import javax.xml.stream.XMLStreamWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author xiadawei
 * @Date 2024-01-26 22:16:00
 * @Description TODO
 */
public class CDataHandler implements InvocationHandler {

    private static Method writeCharactersMethod = null;

    private XMLStreamWriter writer;

    public CDataHandler(XMLStreamWriter writer) {
        this.writer = writer;
    }

    static {
        try {
            writeCharactersMethod = XMLStreamWriter.class.getDeclaredMethod("writeCharacters", char[].class, int.class, int.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (writeCharactersMethod.equals(method)) {
            String text = new String((char[]) args[0]);
            if (StringUtils.startsWith(text, "<![CDATA[") && StringUtils.endsWith(text, "]]>")) {
                writer.writeCData(text.substring(9, text.length() - 3));
                return null;
            }
        }
        return method.invoke(writer, args);
    }
}
