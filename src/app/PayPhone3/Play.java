package app.PayPhone3;

import app.PayPhone3.dao.*;
import app.PayPhone3.dto.*;
import app.PayPhone3.data.*;

import java.io.IOException;
import java.util.List;

public class Play {
    public static void main(String[] args) throws IOException {
        PhoneOrderDAO phoneOrderDAO = new PhoneOrderDAO();
        CompanyDAO companyDAO = new CompanyDAO();
        StorageDAO storageDAO = new StorageDAO();
        PlanDAO planDAO = new PlanDAO();
        SpeedDAO speedDAO = new SpeedDAO();
        AmountDAO amountDAO = new AmountDAO();

        List<CompanyDTO> companies = companyDAO.getAllCompanies();
        List<StorageDTO> storages = storageDAO.getAllStorages();
        List<PlanDTO> plans = planDAO.getAllPlans();

        data.start(companies, storages, plans, phoneOrderDAO, speedDAO, amountDAO);
    }
}