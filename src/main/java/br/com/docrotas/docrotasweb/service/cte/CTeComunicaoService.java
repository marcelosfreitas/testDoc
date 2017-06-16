package br.com.docrotas.docrotasweb.service.cte;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;

public class CTeComunicaoService {
	
	public void recepicionarLote() {
	}
	
	protected Call createCall() throws Exception {
		Service service = new Service
		
        Call call = ( Call ) service.createCall();
        call.setTargetEndpointAddress(url);

        call.setOperation(createOperation());
        call.setUseSOAPAction(true);

        if(getSoapActionURI() != null) {
            call.setSOAPActionURI(getSoapActionURI());
        }

        call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP12_ENC);
        call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(SOAP12Constants.SOAP12_CONSTANTS);
        call.setOperationName(new QName("", getOperationName()));

        return call;
    }

}
