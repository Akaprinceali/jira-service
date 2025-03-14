package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.dao.PrivilegeDao;
import com.fireflink.jiraservice.dao.UserDao;
import com.fireflink.jiraservice.model.constant.CommonConstants;
import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.PrivilegeDTO;
import com.fireflink.jiraservice.model.entity.Privilege;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.service.PrivilegeService;
import com.fireflink.jiraservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeDao privilegeDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<ApiResponseDTO<String>> savePrivilege(PrivilegeDTO privilegeDTO, String email) {

        Optional<User> user = userDao.findByEmail(email);

        if(user.isPresent() && user.get().getPrivilege().equalsIgnoreCase(CommonConstants.ADMIN)){

            Query query=new Query(Criteria.where("privilegeName").is(StringUtils.normalizeSpace(privilegeDTO.getPrivilegeName())));

            if(!mongoTemplate.exists(query, Privilege.class)){

                Privilege privilege = modelMapper.map(privilegeDTO, Privilege.class);
                privilege.setCreateEntity(user.get().getUsername(),user.get().getEmail());
                privilege.setCreatedOn(LocalDateTime.now());

                return ResponseUtil.getOkResponse(privilegeDao.savePrivilege(privilege),"Privilege added successfully");
            }
            return ResponseUtil.getOkResponse(null,"Privilege already exist");
        }

         return ResponseUtil.getBadRequestResponse(null,"Only Admin is authorized to commit Privilege");

    }

    @Override
    public ResponseEntity<ApiResponseDTO<List<String>>> getPrivilegeNames() {

        List<Privilege> privileges = mongoTemplate.findAll(Privilege.class);

        List<String> privilegeNames = privileges.parallelStream().map(Privilege::getPrivilegeName).toList();

        if (!privilegeNames.isEmpty()){
            return ResponseUtil.getOkResponse(privilegeNames,"Privilege fetched successfully");
        }

        return ResponseUtil.getBadRequestResponse(privilegeNames,"Privilege not exist");
    }

    @Override
    public ResponseEntity<ApiResponseDTO<List<PrivilegeDTO>>> getAllPrivileges() {

        List<PrivilegeDTO> privileges=privilegeDao.getAllPrivileges();

        return ResponseUtil.getOkResponse(privileges,"Privileges fetched successfully");
    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> updatePrivilege(PrivilegeDTO privilegeDTO, String email) {

        Optional<User> user = userDao.findByEmail(email);

        if(user.isPresent() && user.get().getPrivilege().equalsIgnoreCase("ADMIN")){

            Query query=new Query(Criteria.where("privilegeName").is(StringUtils.normalizeSpace(privilegeDTO.getPrivilegeName())));

            Privilege privilege = mongoTemplate.findOne(query, Privilege.class);

            if(!Objects.isNull(privilege)){

                Privilege updatedPrivilege = modelMapper.map(privilegeDTO, Privilege.class);
                privilege.setModifiedEntity(user.get().getUsername(),user.get().getEmail());
                privilege.setLastUpdateOn(LocalDateTime.now());
                return ResponseUtil.getOkResponse(privilegeDao.savePrivilege(updatedPrivilege),"Privilege updated successfully");
            }
            return ResponseUtil.getOkResponse(null,"Privilege not exist");
        }

        return ResponseUtil.getBadRequestResponse(null,"Only Admin is authorized to commit Privilege");

    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> deletePrivilegeById(String email, String privilegeId) {

        Optional<User> user = userDao.findByEmail(email);

        if(user.isPresent() && user.get().getPrivilege().equalsIgnoreCase("ADMIN")){

            Query query=new Query(Criteria.where("privilegeId").is(StringUtils.normalizeSpace(privilegeId)));

            Privilege privilege = mongoTemplate.findOne(query, Privilege.class);

            if(!Objects.isNull(privilege)){

                privilegeDao.deletePrivilegeById(privilegeId);
                return ResponseUtil.getOkResponse(null,"Privilege deleted successfully");
            }
            return ResponseUtil.getOkResponse(null,"Privilege not exist");
        }

        return ResponseUtil.getBadRequestResponse(null,"Only Admin is authorized to commit Privilege");

    }
}
