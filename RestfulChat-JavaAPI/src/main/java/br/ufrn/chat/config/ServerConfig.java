package br.ufrn.chat.config;

/**
 * Classe de constantes para as configurações das urls dos serviços.
 */
public final class ServerConfig {

    //Url base do serviço
    public static final String SERVER_BASE_URL = "http://localhost:8081";

    //CHAT RESOURCES
    public static final String REGISTER_NOTIFICATION_URL = "%s/chat/registernotification/?username=%s";
    public static final String UNREGISTER_NOTIFICATION_URL = "%s/chat/unregisternotification/?username=%s";
    public static final String SEND_MESSAGE_URL = "%s/chat/send";

    //GROUP RESOURCES
    public static final String JOIN_GROUP_URL = "%s/groups/join";
    public static final String FIND_GROUP_URL = "%s/groups/find/?groupId=%s";
    public static final String CREATE_GROUP_URL = "%s/groups/create";
    public static final String FIND_USER_GROUPS_URL = "%s/groups/list/?username=%s";
    public static final String FIND_ALL_GROUPS_URL = "%s/groups/listall";
    public static final String QUIT_GROUP_URL = "%s/groups/quit";

    //USER RESOURCES
    public static final String REGISTER_USER_URL = "%s/users/register";

    private ServerConfig(){}
}
