package org.crsoft.cartonplast.common.service;

import org.crsoft.cartonplast.vo.RoleVo;
import org.crsoft.cartonplast.vo.TokenVo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Objects;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.URL_USER;

/**
 * @author jyepez on 19/5/2022
 */
@Service
public class RoleService {

    private String getToken() throws Exception {
        String urlToken = "https://auth-test.carton-plast.com/realms/carton-plast/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type","password");
        map.add("client_id","carton-plast-frontend");
        map.add("username","cpadmin");
        map.add("password","Password01");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<TokenVo> exchange = new RestTemplate().exchange(urlToken, HttpMethod.POST, entity, TokenVo.class);
                return Objects.requireNonNull(exchange.getBody()).getAccess_token();
        }catch (Exception e){
            throw new Exception();
        }
    }

    public Collection<RoleVo> findAllRole() throws Exception {
        String url = URL_USER.concat("/role/findAllRole");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer "+getToken());

            HttpEntity request = new HttpEntity(headers);

            ResponseEntity<Collection> exchange = new RestTemplate().exchange(url, HttpMethod.GET, request, Collection.class);

            return  exchange.getBody();
        }catch (Exception e){
            throw  new Exception("No data");
        }
    }
}
