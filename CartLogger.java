import burp.*;

import java.io.PrintWriter;

public class CartLogger implements IBurpExtender, IHttpListener {

    private PrintWriter stdout;
    private IExtensionHelpers helpers;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        callbacks.setExtensionName("Simple Cart Logger");
        helpers = callbacks.getHelpers();
        stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.registerHttpListener(this);
        stdout.println("Cart Logger loaded.");
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
        if (!messageIsRequest) return;

        IRequestInfo reqInfo = helpers.analyzeRequest(messageInfo);
        String url = reqInfo.getUrl().toString();

        if (url.contains("/add-to-cart")) {
            stdout.println("\n[+] Cart Request:");
            stdout.println("Method: " + reqInfo.getMethod());
            stdout.println("URL: " + url);
        }
    }
}
