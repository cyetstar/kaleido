package cc.onelooker.kaleido.nfo;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @Author xiadawei
 * @Date 2023-12-19 14:22:00
 * @Description TODO
 */
public class CDataAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        return new StringBuilder("<![CDATA[").append(v).append("]]>")
                .toString();
    }
}
