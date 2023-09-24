package com.ezbytz.keycloak.mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.ezbytz.keycloak.configs.utils.UUIDGenerator;
import com.ezbytz.keycloak.models.requests.ClientRegistrationRequest;
import com.ezbytz.keycloak.models.requests.ClientRoleRequest;
import com.ezbytz.keycloak.models.requests.ClientScopeRegistrationRequest;
import com.ezbytz.keycloak.models.requests.RealmRegistrationRequest;
import com.ezbytz.keycloak.models.requests.UserRegistrationRequest;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.ClientScopeRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RealmMapper {

    RealmRepresentation toRealmRepresentation(RealmRegistrationRequest realmRegistrationRequest);

    List<ClientScopeRepresentation> toClientScopeRepresentationList(List<ClientScopeRegistrationRequest> clientScopes);

    @Mapping(source = "clientName", target = "name")
    ClientRepresentation toClientRepresentation(ClientRegistrationRequest clientRegistrationRequest);

    default RealmRepresentation toCustomRealmRepresentation(RealmRegistrationRequest realmRegistrationRequest) {
        final RealmRepresentation realmRepresentation = toRealmRepresentation(realmRegistrationRequest);
        realmRepresentation.setClients(List.of(toClientRepresentation(realmRegistrationRequest.getClient())));
        realmRepresentation.setClientScopes(toClientScopeRepresentationList(realmRegistrationRequest.getClientScopes()));
        realmRepresentation.setDefaultDefaultClientScopes(realmRegistrationRequest.getDefaultClientScopes());
        realmRepresentation.setRoles(toRolesRepresentation(List.of(realmRegistrationRequest.getClient())));
        realmRepresentation
            .setUsers(
                toCustomUserRepresentation(realmRegistrationRequest.getUsers(), realmRegistrationRequest.getClient().getClientName()));

        return realmRepresentation;
    }

    default RolesRepresentation toRolesRepresentation(List<ClientRegistrationRequest> clientRegistrationRequests) {
        final RolesRepresentation rolesRepresentation = new RolesRepresentation();
        final Map<String, List<RoleRepresentation>> client = clientRegistrationRequests.stream()
            .collect(Collectors.toMap(
                ClientRegistrationRequest::getClientName,
                clientRegistrationRequest -> toRoleRepresentationList(clientRegistrationRequest.getRoles())));
        rolesRepresentation.setClient(client);
        return rolesRepresentation;
    }

    UserRepresentation toUserRepresentation(UserRegistrationRequest userRegistrationRequest);

    default List<UserRepresentation> toCustomUserRepresentation(List<UserRegistrationRequest> userRegistrationRequests,
                                                                final String clientName) {
        return userRegistrationRequests.stream().map(userRegistrationRequest -> {
            final MultiValueMap<String, String> client = new LinkedMultiValueMap<>();
            final List<String> clientRoles = userRegistrationRequest.getRoles().stream().toList();
            client.put(clientName, clientRoles);

            final CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(Boolean.TRUE);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(UUIDGenerator.generateUUID());

            final UserRepresentation userRepresentation = toUserRepresentation(userRegistrationRequest);
            userRepresentation.setClientRoles(client);
            userRepresentation.setCredentials(List.of(credentialRepresentation));
            return userRepresentation;
        }).toList();
    }

    default List<RoleRepresentation> toRoleRepresentationList(Set<ClientRoleRequest> clientRoleRequests) {
        return clientRoleRequests.stream()
            .map(this::toRoleRepresentation)
            .toList();
    }

    @Mapping(source = "roleName", target = "name")
    RoleRepresentation toRoleRepresentation(ClientRoleRequest clientRoleRequest);

}
