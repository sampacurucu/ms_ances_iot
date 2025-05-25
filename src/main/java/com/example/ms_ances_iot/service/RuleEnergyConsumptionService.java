package com.example.ms_ances_iot.service;

import com.example.ms_ances_iot.dto.*;
import com.example.ms_ances_iot.entity.CondicionEntity;
import com.example.ms_ances_iot.entity.RuleEnergyConsumptionEntity;
import com.example.ms_ances_iot.mapper.RuleEnergyConsumptionMapper;
import com.example.ms_ances_iot.rabbitmq.MensajeCondicionDto;
import com.example.ms_ances_iot.rabbitmq.ConfiguracionCondicionSender;
import com.example.ms_ances_iot.rabbitmq.ConfiguracionReglaSender;
import com.example.ms_ances_iot.rabbitmq.MensajeReglaEnergeticaDto;
import com.example.ms_ances_iot.repository.RuleEnergyConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RuleEnergyConsumptionService {

    private final RuleEnergyConsumptionRepository repo;
    private final RuleEnergyConsumptionMapper mapper;
    private final ConfiguracionReglaSender reglaSender;
    private final ConfiguracionCondicionSender condicionSender;


    public void guardarReglasEnergeticas(List<RuleEnergyConsumptionDto> reglasDto) {
        List<RuleEnergyConsumptionEntity> entidades = reglasDto.stream()
                .map(mapper::toEntity)
                .toList();
        // repo.saveAll(entidades);
        
        //envio a la cola
        List<RuleEnergyConsumptionEntity> guardadas = repo.saveAll(entidades);
        for (RuleEnergyConsumptionEntity regla : guardadas) {
            Map<String, Object> ruleMap = new HashMap<>();
            ruleMap.put("idLocalRuleEnergyConsumption", regla.getId());
            ruleMap.put("operatorRule", regla.getOperadorCondiciones());
            ruleMap.put("newFrequency", regla.getFrecuencia());
            ruleMap.put("idDevice", List.of(regla.getDispositivo().getId())); // puedes adaptar esto

            MensajeReglaEnergeticaDto mensaje = new MensajeReglaEnergeticaDto(ruleMap);
            reglaSender.enviar(mensaje);
            for (CondicionEntity condicion : regla.getCondiciones()) {
                MensajeCondicionDto dto = new MensajeCondicionDto(
                    condicion.getId(),
                    condicion.getOperador(),
                    condicion.getValor(),
                    regla.getId()
                );
                condicionSender.enviar(dto);
            }

        }
    }

    public List<ReglaEnergeticaExpandidaDto> obtenerPorProceso(Long procesoId) {
        List<RuleEnergyConsumptionEntity> reglas = repo.findByProcesoIniciadoId(procesoId);
        return mapper.toExpandedDtoList(reglas);
    }
}
