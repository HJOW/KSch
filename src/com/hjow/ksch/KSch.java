/*
(Forked by JCraft JSch project - Copyright (c) 2002-2016 ymnk, JCraft,Inc. All rights reserved.)
(Please visit http://www.jcraft.com/jsch/ for details.)

Copyright (c) HJOW All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
     this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright 
     notice, this list of conditions and the following disclaimer in 
     the documentation and/or other materials provided with the distribution.

  3. The names of the authors may not be used to endorse or promote products
     derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JCRAFT,
INC. OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
/*
Copyright (c) 2002-2016 ymnk, JCraft,Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
     this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright 
     notice, this list of conditions and the following disclaimer in 
     the documentation and/or other materials provided with the distribution.

  3. The names of the authors may not be used to endorse or promote products
     derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JCRAFT,
INC. OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.hjow.ksch;

import java.io.InputStream;
import java.util.Vector;

public class KSch {
  /**
   * The version number.
   */
  public static final String VERSION  = "0.0.1";

  static java.util.Hashtable<String, String> config=new java.util.Hashtable<String, String>();
  static{
    config.put("kex", "ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha256,diffie-hellman-group-exchange-sha1,diffie-hellman-group1-sha1");
    config.put("server_host_key", "ssh-rsa,ssh-dss,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521");
    config.put("cipher.s2c", 
               "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-ctr,aes192-cbc,aes256-ctr,aes256-cbc");
    config.put("cipher.c2s",
               "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-ctr,aes192-cbc,aes256-ctr,aes256-cbc");

    config.put("mac.s2c", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
    config.put("mac.c2s", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
    config.put("compression.s2c", "none");
    config.put("compression.c2s", "none");

    config.put("lang.s2c", "");
    config.put("lang.c2s", "");

    config.put("compression_level", "6");

    config.put("diffie-hellman-group-exchange-sha1", 
                                "com.hjow.ksch.DHGEX");
    config.put("diffie-hellman-group1-sha1", 
	                        "com.hjow.ksch.DHG1");
    config.put("diffie-hellman-group14-sha1", 
               "com.hjow.ksch.DHG14");    // available since JDK8.
    config.put("diffie-hellman-group-exchange-sha256", 
               "com.hjow.ksch.DHGEX256"); // available since JDK1.4.2.
                                            // On JDK8, 2048bits will be used.
    config.put("ecdsa-sha2-nistp256", "com.hjow.ksch.jce.SignatureECDSA");
    config.put("ecdsa-sha2-nistp384", "com.hjow.ksch.jce.SignatureECDSA");
    config.put("ecdsa-sha2-nistp521", "com.hjow.ksch.jce.SignatureECDSA");

    config.put("ecdh-sha2-nistp256", "com.hjow.ksch.DHEC256");
    config.put("ecdh-sha2-nistp384", "com.hjow.ksch.DHEC384");
    config.put("ecdh-sha2-nistp521", "com.hjow.ksch.DHEC521");

    config.put("ecdh-sha2-nistp", "com.hjow.ksch.jce.ECDHN");

    config.put("dh",            "com.hjow.ksch.jce.DH");
    config.put("3des-cbc",      "com.hjow.ksch.jce.TripleDESCBC");
    config.put("blowfish-cbc",  "com.hjow.ksch.jce.BlowfishCBC");
    config.put("hmac-sha1",     "com.hjow.ksch.jce.HMACSHA1");
    config.put("hmac-sha1-96",  "com.hjow.ksch.jce.HMACSHA196");
    config.put("hmac-sha2-256",  "com.hjow.ksch.jce.HMACSHA256");
    // The "hmac-sha2-512" will require the key-length 2048 for DH,
    // but Sun's JCE has not allowed to use such a long key.
    //config.put("hmac-sha2-512",  "com.hjow.ksch.jce.HMACSHA512");
    config.put("hmac-md5",      "com.hjow.ksch.jce.HMACMD5");
    config.put("hmac-md5-96",   "com.hjow.ksch.jce.HMACMD596");
    config.put("sha-1",         "com.hjow.ksch.jce.SHA1");
    config.put("sha-256",         "com.hjow.ksch.jce.SHA256");
    config.put("sha-384",         "com.hjow.ksch.jce.SHA384");
    config.put("sha-512",         "com.hjow.ksch.jce.SHA512");
    config.put("md5",           "com.hjow.ksch.jce.MD5");
    config.put("signature.dss", "com.hjow.ksch.jce.SignatureDSA");
    config.put("signature.rsa", "com.hjow.ksch.jce.SignatureRSA");
    config.put("signature.ecdsa", "com.hjow.ksch.jce.SignatureECDSA");
    config.put("keypairgen.dsa",   "com.hjow.ksch.jce.KeyPairGenDSA");
    config.put("keypairgen.rsa",   "com.hjow.ksch.jce.KeyPairGenRSA");
    config.put("keypairgen.ecdsa", "com.hjow.ksch.jce.KeyPairGenECDSA");
    config.put("random",        "com.hjow.ksch.jce.Random");

    config.put("none",           "com.hjow.ksch.CipherNone");

    config.put("aes128-cbc",    "com.hjow.ksch.jce.AES128CBC");
    config.put("aes192-cbc",    "com.hjow.ksch.jce.AES192CBC");
    config.put("aes256-cbc",    "com.hjow.ksch.jce.AES256CBC");

    config.put("aes128-ctr",    "com.hjow.ksch.jce.AES128CTR");
    config.put("aes192-ctr",    "com.hjow.ksch.jce.AES192CTR");
    config.put("aes256-ctr",    "com.hjow.ksch.jce.AES256CTR");
    config.put("3des-ctr",      "com.hjow.ksch.jce.TripleDESCTR");
    config.put("arcfour",      "com.hjow.ksch.jce.ARCFOUR");
    config.put("arcfour128",      "com.hjow.ksch.jce.ARCFOUR128");
    config.put("arcfour256",      "com.hjow.ksch.jce.ARCFOUR256");

    config.put("userauth.none",    "com.hjow.ksch.UserAuthNone");
    config.put("userauth.password",    "com.hjow.ksch.UserAuthPassword");
    config.put("userauth.keyboard-interactive",    "com.hjow.ksch.UserAuthKeyboardInteractive");
    config.put("userauth.publickey",    "com.hjow.ksch.UserAuthPublicKey");
    config.put("userauth.gssapi-with-mic",    "com.hjow.ksch.UserAuthGSSAPIWithMIC");
    config.put("gssapi-with-mic.krb5",    "com.hjow.ksch.jgss.GSSContextKrb5");

    config.put("zlib",             "com.hjow.ksch.jcraft.Compression");
    config.put("zlib@openssh.com", "com.hjow.ksch.jcraft.Compression");

    config.put("pbkdf", "com.hjow.ksch.jce.PBKDF");

    config.put("StrictHostKeyChecking",  "ask");
    config.put("HashKnownHosts",  "no");

    config.put("PreferredAuthentications", "gssapi-with-mic,publickey,keyboard-interactive,password");

    config.put("CheckCiphers", "aes256-ctr,aes192-ctr,aes128-ctr,aes256-cbc,aes192-cbc,aes128-cbc,3des-ctr,arcfour,arcfour128,arcfour256");
    config.put("CheckKexes", "diffie-hellman-group14-sha1,ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521");
    config.put("CheckSignatures", "ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521");

    config.put("MaxAuthTries", "6");
    config.put("ClearAllForwardings", "no");
  }

  private java.util.Vector<Session> sessionPool = new java.util.Vector<Session>();

  private IdentityRepository defaultIdentityRepository =
    new LocalIdentityRepository(this);

  private IdentityRepository identityRepository = defaultIdentityRepository;

  private ConfigRepository configRepository = null;

  /**
   * Sets the <code>identityRepository</code>, which will be referred
   * in the public key authentication.
   *
   * @param identityRepository if <code>null</code> is given,
   * the default repository, which usually refers to ~/.ssh/, will be used.
   *
   * @see #getIdentityRepository()
   */
  public synchronized void setIdentityRepository(IdentityRepository identityRepository){
    if(identityRepository == null){
      this.identityRepository = defaultIdentityRepository;
    }
    else{
      this.identityRepository = identityRepository;
    }
  }

  public synchronized IdentityRepository getIdentityRepository(){
    return this.identityRepository;
  }

  public ConfigRepository getConfigRepository() {
    return this.configRepository;
  }

  public void setConfigRepository(ConfigRepository configRepository) {
    this.configRepository = configRepository;
  }

  private HostKeyRepository known_hosts=null;

  private static final Logger DEVNULL=new Logger(){
      public boolean isEnabled(int level){return false;}
      public void log(int level, String message){}
    };
  static Logger logger=DEVNULL;

  public KSch(){
    /*
    // The JCE of Sun's Java5 on Mac OS X has the resource leak bug
    // in calculating HMAC, so we need to use our own implementations.
    try{
      String osname=(String)(System.getProperties().get("os.name"));
      if(osname!=null && osname.equals("Mac OS X")){
        config.put("hmac-sha1",     "com.hjow.ksch.jcraft.HMACSHA1"); 
        config.put("hmac-md5",      "com.hjow.ksch.jcraft.HMACMD5"); 
        config.put("hmac-md5-96",   "com.hjow.ksch.jcraft.HMACMD596"); 
        config.put("hmac-sha1-96",  "com.hjow.ksch.jcraft.HMACSHA196"); 
      }
    }
    catch(Exception e){
    }
    */
  }

  /**
   * Instantiates the <code>Session</code> object with
   * <code>host</code>.  The user name and port number will be retrieved from
   * ConfigRepository.  If user name is not given,
   * the system property "user.name" will be referred. 
   *
   * @param host hostname
   *
   * @throws JSchException
   *         if <code>username</code> or <code>host</code> are invalid.
   *
   * @return the instance of <code>Session</code> class.
   *
   * @see #getSession(String username, String host, int port)
   * @see com.hjow.ksch.Session
   * @see com.hjow.ksch.ConfigRepository
   */
  public Session getSession(String host)
     throws JSchException {
    return getSession(null, host, 22);
  }

  /**
   * Instantiates the <code>Session</code> object with
   * <code>username</code> and <code>host</code>.
   * The TCP port 22 will be used in making the connection.
   * Note that the TCP connection must not be established
   * until Session#connect().
   *
   * @param username user name
   * @param host hostname
   *
   * @throws JSchException
   *         if <code>username</code> or <code>host</code> are invalid.
   *
   * @return the instance of <code>Session</code> class.
   *
   * @see #getSession(String username, String host, int port)
   * @see com.hjow.ksch.Session
   */
  public Session getSession(String username, String host)
     throws JSchException {
    return getSession(username, host, 22);
  }

  /**
   * Instantiates the <code>Session</code> object with given
   * <code>username</code>, <code>host</code> and <code>port</code>.
   * Note that the TCP connection must not be established
   * until Session#connect().
   *
   * @param username user name
   * @param host hostname
   * @param port port number
   *
   * @throws JSchException
   *         if <code>username</code> or <code>host</code> are invalid.
   *
   * @return the instance of <code>Session</code> class.
   *
   * @see #getSession(String username, String host, int port)
   * @see com.hjow.ksch.Session
   */
  public Session getSession(String username, String host, int port) throws JSchException {
    if(host==null){
      throw new JSchException("host must not be null.");
    }
    Session s = new Session(this, username, host, port); 
    return s;
  }

  protected void addSession(Session session){
    synchronized(sessionPool){
      sessionPool.addElement(session);
    }
  }

  protected boolean removeSession(Session session){
    synchronized(sessionPool){
      return sessionPool.remove(session);
    }
  }

  /**
   * Sets the hostkey repository.
   *
   * @param hkrepo
   *
   * @see com.hjow.ksch.HostKeyRepository
   * @see com.hjow.ksch.KnownHosts
   */
  public void setHostKeyRepository(HostKeyRepository hkrepo){
    known_hosts=hkrepo;
  }

  /**
   * Sets the instance of <code>KnownHosts</code>, which refers
   * to <code>filename</code>.
   *
   * @param filename filename of known_hosts file.
   *
   * @throws JSchException
   *         if the given filename is invalid.
   *
   * @see com.hjow.ksch.KnownHosts
   */
  public void setKnownHosts(String filename) throws JSchException{
    if(known_hosts==null) known_hosts=new KnownHosts(this);
    if(known_hosts instanceof KnownHosts){
      synchronized(known_hosts){
	((KnownHosts)known_hosts).setKnownHosts(filename); 
      }
    }
  }

  /**
   * Sets the instance of <code>KnownHosts</code> generated with
   * <code>stream</code>.
   *
   * @param stream the instance of InputStream from known_hosts file.
   *
   * @throws JSchException
   *         if an I/O error occurs.
   *
   * @see com.hjow.ksch.KnownHosts
   */
  public void setKnownHosts(InputStream stream) throws JSchException{ 
    if(known_hosts==null) known_hosts=new KnownHosts(this);
    if(known_hosts instanceof KnownHosts){
      synchronized(known_hosts){
	((KnownHosts)known_hosts).setKnownHosts(stream); 
      }
    }
  }

  /**
   * Returns the current hostkey repository.
   * By the default, this method will the instance of <code>KnownHosts</code>.
   *
   * @return current hostkey repository.
   *
   * @see com.hjow.ksch.HostKeyRepository
   * @see com.hjow.ksch.KnownHosts
   */
  public HostKeyRepository getHostKeyRepository(){ 
    if(known_hosts==null) known_hosts=new KnownHosts(this);
    return known_hosts; 
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   *
   * @param prvkey filename of the private key.
   *
   * @throws JSchException if <code>prvkey</code> is invalid.
   *
   * @see #addIdentity(String prvkey, String passphrase)
   */
  public void addIdentity(String prvkey) throws JSchException{
    addIdentity(prvkey, (byte[])null);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param prvkey filename of the private key.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   * @throws JSchException if <code>passphrase</code> is not right.
   *
   * @see #addIdentity(String prvkey, byte[] passphrase)
   */
  public void addIdentity(String prvkey, String passphrase) throws JSchException{
    byte[] _passphrase=null;
    if(passphrase!=null){
      _passphrase=Util.str2byte(passphrase);
    }
    addIdentity(prvkey, _passphrase);
    if(_passphrase!=null)
      Util.bzero(_passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param prvkey filename of the private key.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   * @throws JSchException if <code>passphrase</code> is not right.
   *
   * @see #addIdentity(String prvkey, String pubkey, byte[] passphrase)
   */
  public void addIdentity(String prvkey, byte[] passphrase) throws JSchException{
    Identity identity=IdentityFile.newInstance(prvkey, null, this);
    addIdentity(identity, passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param prvkey filename of the private key.
   * @param pubkey filename of the public key.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   * @throws JSchException if <code>passphrase</code> is not right.
   */
  public void addIdentity(String prvkey, String pubkey, byte[] passphrase) throws JSchException{
    Identity identity=IdentityFile.newInstance(prvkey, pubkey, this);
    addIdentity(identity, passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param name name of the identity to be used to
                 retrieve it in the identityRepository.
   * @param prvkey private key in byte array.
   * @param pubkey public key in byte array.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   */
  public void addIdentity(String name, byte[]prvkey, byte[]pubkey, byte[] passphrase) throws JSchException{
    Identity identity=IdentityFile.newInstance(name, prvkey, pubkey, this);
    addIdentity(identity, passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param identity private key.
   * @param passphrase passphrase for <code>identity</code>.
   *
   * @throws JSchException if <code>passphrase</code> is not right.
   */
  public void addIdentity(Identity identity, byte[] passphrase) throws JSchException{
    if(passphrase!=null){
      try{ 
        byte[] goo=new byte[passphrase.length];
        System.arraycopy(passphrase, 0, goo, 0, passphrase.length);
        passphrase=goo;
        identity.setPassphrase(passphrase); 
      }
      finally{
        Util.bzero(passphrase);
      }
    }

    if(identityRepository instanceof LocalIdentityRepository){
      ((LocalIdentityRepository)identityRepository).add(identity);
    }
    else if(identity instanceof IdentityFile && !identity.isEncrypted()) {
      identityRepository.add(((IdentityFile)identity).getKeyPair().forSSHAgent());
    }
    else {
      synchronized(this){
        if(!(identityRepository instanceof IdentityRepository.Wrapper)){
          setIdentityRepository(new IdentityRepository.Wrapper(identityRepository));
        }
      }
      ((IdentityRepository.Wrapper)identityRepository).add(identity);
    }
  }

  /**
   * @deprecated use #removeIdentity(Identity identity)
   */
  public void removeIdentity(String name) throws JSchException{
    Vector identities = identityRepository.getIdentities();
    for(int i=0; i<identities.size(); i++){
      Identity identity=(Identity)(identities.elementAt(i));
      if(!identity.getName().equals(name))
        continue;
      if(identityRepository instanceof LocalIdentityRepository){
        ((LocalIdentityRepository)identityRepository).remove(identity);
      }
      else
        identityRepository.remove(identity.getPublicKeyBlob());
    }
  }

  /**
   * Removes the identity from identityRepository.
   *
   * @param identity the indentity to be removed.
   *
   * @throws JSchException if <code>identity</code> is invalid.
   */
  public void removeIdentity(Identity identity) throws JSchException{
    identityRepository.remove(identity.getPublicKeyBlob());
  }

  /**
   * Lists names of identities included in the identityRepository.
   *
   * @return names of identities
   *
   * @throws JSchException if identityReposory has problems.
   */
  public Vector getIdentityNames() throws JSchException{
    Vector foo=new Vector();
    Vector identities = identityRepository.getIdentities();
    for(int i=0; i<identities.size(); i++){
      Identity identity=(Identity)(identities.elementAt(i));
      foo.addElement(identity.getName());
    }
    return foo;
  }

  /**
   * Removes all identities from identityRepository.
   *
   * @throws JSchException if identityReposory has problems.
   */
  public void removeAllIdentity() throws JSchException{
    identityRepository.removeAll();
  }

  /**
   * Returns the config value for the specified key.
   *
   * @param key key for the configuration.
   * @return config value
   */
  public static String getConfig(String key){ 
    synchronized(config){
      return (String)(config.get(key));
    } 
  }

  /**
   * Sets or Overrides the configuration.
   *
   * @param newconf configurations
   */
  public static void setConfig(java.util.Hashtable newconf){
    synchronized(config){
      for(java.util.Enumeration e=newconf.keys() ; e.hasMoreElements() ;) {
	String key=(String)(e.nextElement());
	config.put(key, (String)(newconf.get(key)));
      }
    }
  }

  /**
   * Sets or Overrides the configuration.
   *
   * @param key key for the configuration
   * @param value value for the configuration
   */
  public static void setConfig(String key, String value){
    config.put(key, value);
  }

  /**
   * Sets the logger
   *
   * @param logger logger
   *
   * @see com.hjow.ksch.Logger
   */
  public static void setLogger(Logger logger){
    if(logger==null) logger=DEVNULL;
    KSch.logger=logger;
  }

  static Logger getLogger(){
    return logger;
  }
}
