package org.gy.framework.util.weixin.util;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class SimpleXpp3Driver extends Xpp3Driver {

    @Override
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new SimplePrettyPrintWriter(out);
    }

    /**
     * 功能描述：
     * 
     * @version 2.0.0
     * @author guanyang
     */
    private class SimplePrettyPrintWriter extends PrettyPrintWriter {
        // 对字符xml节点的转换都增加CDATA标记
        boolean cdata = false;

        private SimplePrettyPrintWriter(Writer writer) {
            super(writer);
        }

        @Override
        public void startNode(String name,
                              Class clazz) {
            if (clazz.isAssignableFrom(String.class)) {
                cdata = true;
            } else {
                cdata = false;
            }
            super.startNode(name, clazz);
        }

        @Override
        protected void writeText(QuickWriter writer,
                                 String text) {
            if (cdata) {
                writer.write("<![CDATA[");
                writer.write(text);
                writer.write("]]>");
            } else {
                writer.write(text);
            }
        }
    }
}
