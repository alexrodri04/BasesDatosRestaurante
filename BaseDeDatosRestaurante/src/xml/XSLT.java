package xml;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLT {
	
	public static void xsltEmpleado() {
		
		String sourcePath = "Empleado.xml";
		String xsltPath = "xslt/xslt_empleado.xslt";
		String resultDir = "xslt/xslt_empleado.html";
	
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new
			StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)),new
			StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

