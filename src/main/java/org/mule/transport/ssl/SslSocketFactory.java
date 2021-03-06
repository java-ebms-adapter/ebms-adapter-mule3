/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.ssl;

import org.mule.api.security.tls.TlsConfiguration;
import org.mule.transport.tcp.AbstractTcpSocketFactory;
import org.mule.transport.tcp.TcpSocketKey;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocket;

public class SslSocketFactory extends AbstractTcpSocketFactory
{

    private TlsConfiguration tls;

    public SslSocketFactory(TlsConfiguration tls)
    {
        this.tls = tls;
    }

    protected Socket createSocket(TcpSocketKey key) throws IOException
    {
        try
        {
            SSLSocket socket = (SSLSocket)tls.getSocketFactory().createSocket(key.getInetAddress(), key.getPort());
            //PATCH
      		  socket.setEnabledCipherSuites(new String[]{"TLS_DHE_RSA_WITH_AES_128_CBC_SHA","TLS_RSA_WITH_AES_128_CBC_SHA"});
            return socket;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw (IOException) new IOException(e.getMessage()).initCause(e);
        }
        catch (KeyManagementException e)
        {
            throw (IOException) new IOException(e.getMessage()).initCause(e);
        }
    }

}
