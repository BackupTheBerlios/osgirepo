package gov.nist.core.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * basic interface to the network layer
 *
 * @author m.andrews
 *
 */
public interface NetworkLayer {

    /**
     * Creates a server with the specified port, listen backlog, and local IP address to bind to. 
     * comparable to "new java.net.ServerSocket(port,backlog,bindAddress);"
     * 
     * @param port
     * @param backlog
     * @param bindAddress
     * @return the server socket
     */
    public ServerSocket createServerSocket(int port, int backlog,
            InetAddress bindAddress) throws IOException;

    /**
     * Creates a stream socket and connects it to the specified port number at the specified IP address.
     * comparable to "new java.net.Socket(address, port);"
     * 
     * @param address
     * @param port
     * @return the socket
     */
    public Socket createSocket(InetAddress address, int port) throws IOException;
    
    /**
     * Constructs a datagram socket and binds it to any available port on the local host machine.
     * comparable to "new java.net.DatagramSocket();"
     * 
     * @return the datagram socket
     */
    public DatagramSocket createDatagramSocket() throws SocketException;

    /**
     * Creates a datagram socket, bound to the specified local address.
     * comparable to "new java.net.DatagramSocket(port,laddr);"
     * 
     * @param port
     * @param laddr
     * @return the datagram socket
     */
    public DatagramSocket createDatagramSocket(int port, InetAddress laddr)
            throws SocketException;

}