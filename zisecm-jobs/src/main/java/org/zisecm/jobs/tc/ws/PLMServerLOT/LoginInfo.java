/**
 * LoginInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

public class LoginInfo  implements java.io.Serializable {
    private java.lang.String serverHost;

    private java.lang.String userID;

    private java.lang.String password;

    private java.lang.String discriminator;

    public LoginInfo() {
    }

    public LoginInfo(
           java.lang.String serverHost,
           java.lang.String userID,
           java.lang.String password,
           java.lang.String discriminator) {
           this.serverHost = serverHost;
           this.userID = userID;
           this.password = password;
           this.discriminator = discriminator;
    }


    /**
     * Gets the serverHost value for this LoginInfo.
     * 
     * @return serverHost
     */
    public java.lang.String getServerHost() {
        return serverHost;
    }


    /**
     * Sets the serverHost value for this LoginInfo.
     * 
     * @param serverHost
     */
    public void setServerHost(java.lang.String serverHost) {
        this.serverHost = serverHost;
    }


    /**
     * Gets the userID value for this LoginInfo.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this LoginInfo.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the password value for this LoginInfo.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this LoginInfo.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the discriminator value for this LoginInfo.
     * 
     * @return discriminator
     */
    public java.lang.String getDiscriminator() {
        return discriminator;
    }


    /**
     * Sets the discriminator value for this LoginInfo.
     * 
     * @param discriminator
     */
    public void setDiscriminator(java.lang.String discriminator) {
        this.discriminator = discriminator;
    }

   

}
