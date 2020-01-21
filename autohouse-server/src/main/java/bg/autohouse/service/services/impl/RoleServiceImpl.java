package bg.autohouse.service.services.impl;


import bg.autohouse.data.repositories.RoleRepository;
import bg.autohouse.service.models.RoleServiceModel;
import bg.autohouse.service.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Override
    public Set<RoleServiceModel> getAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(r -> mapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

}
