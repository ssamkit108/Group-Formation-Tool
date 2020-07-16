package com.dal.catmeclone.Validation;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.SystemConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class PasswordRulesLoader {

    final Logger LOGGER = Logger.getLogger(PasswordRulesLoader.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    ValidationAbstractFactory validationAbstractFactory = abstractFactory.createValidationAbstractFactory();
    ValidationRulesDao ValidationDAO;
    private List<ValidationPolicy> ListofRules;
    private Map<String, ValidationPolicy> HashMapofRules = new HashMap<String, ValidationPolicy>();

    public PasswordRulesLoader() {
        ListofRules = new ArrayList<ValidationPolicy>();
        PrepareListMap();
    }

    private void PrepareListMap() {
        HashMapofRules.put("MaximumLength", validationAbstractFactory.createMaximumLength());
        HashMapofRules.put("MinimumLength", validationAbstractFactory.createMinimumLength());
        HashMapofRules.put("MinimumLower", validationAbstractFactory.createMinimumLower());
        HashMapofRules.put("MinimumUpper", validationAbstractFactory.createMinimumUpper());
        HashMapofRules.put("MinimumSpecial", validationAbstractFactory.createMinimumSpecial());
        HashMapofRules.put("SetNotAllow", validationAbstractFactory.createSetNotAllow());
        HashMapofRules.put("HistoryConstraint", validationAbstractFactory.createHistoryConstraint());
    }

    public void CreateActiveRulesList() throws Exception {
        ValidationDAO = validationAbstractFactory.createValidationRulesDao();
        ListofRules.clear();
        List<String> rules = ValidationDAO.getRulesFromConfig();
        for (String rule : rules) {
            ValidationPolicy ValidationRule = HashMapofRules.get(rule);
            String ruleValue = ValidationDAO.getRulesValueFromConfig(rule);
            ValidationRule.setValue(ruleValue);
            ListofRules.add(ValidationRule);
        }
        LOGGER.info("Activated Rules are:" + ListofRules.toString());
        LOGGER.info("Active Password validation rules fetched from Database.");
    }

    public List<ValidationPolicy> getValidationRulesList() {
        LOGGER.info("Getting Password validation rules.");
        return ListofRules;
    }
}
