package bg.autohouse.service.services.impl;

import bg.autohouse.data.repositories.ColorRepository;
import bg.autohouse.service.models.ColorServiceModel;
import bg.autohouse.service.services.ColorService;
import bg.autohouse.util.ModelMapperWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ModelMapperWrapper modelMapper;

    @Override
    public List<ColorServiceModel> getAllColors() {
        return colorRepository.findAll()
                .stream()
                .map(color -> modelMapper.map(color,ColorServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

}
