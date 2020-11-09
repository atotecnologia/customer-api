package br.com.composable.customerapi.dao;

import java.util.List;
import java.util.Map;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindMap;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import br.com.composable.customerapi.model.Customer;

public interface CustomerDAO extends Transactional<CustomerDAO>{
	
    @SqlUpdate("INSERT INTO customer(name, cpf, gender, birth_date, created_at, update_at, uuid) "
    		+ "	VALUES(:name, :cpf, :gender, :birthDate, :createdAt, :updateAt, :uuid)")
    @GetGeneratedKeys
    Long insert(@BindBean Customer customer);
    
    @RegisterBeanMapper(Customer.class)
    @SqlQuery("SELECT c.* FROM "
    		+ "customer c, "
    		+ "address a "
    		+ "where "
    		+ "c.id=a.customer_id "
    		+ "AND "
    		+ "a.major IS TRUE "
    		+ "AND "
    		+ "(:filter.name is null or c.name = :filter.name) "
    		+ "AND "
    		+ "(:filter.birthDate is null or c.birth_date = :filter.birthDate) "   
    		+ "AND "
    		+ "(:filter.state is null or a.state = :filter.state) "     
    		+ "AND "
    		+ "(:filter.city is null or a.city = :filter.city) "        		
    		+ "ORDER BY "
    		+ "<orderby> <order> ")
    List<Customer> summary(@BindMap("filter") Map<String, Object> filter, 
    					   @Define("orderby") String orderBy,
    					   @Define("order") String order);
    
    @RegisterBeanMapper(Customer.class)
    @SqlQuery("SELECT * FROM customer c WHERE c.cpf=:cpf")
    Customer retrieveByCpf(@Bind("cpf") String cpf);
    
    @RegisterBeanMapper(Customer.class)
    @SqlQuery("SELECT * FROM customer c WHERE c.id=:id")
    Customer retrieve(@Bind("id") Long id);
    
    @SqlUpdate("UPDATE customer SET "
    		+ "name=:name, cpf=:cpf, gender=:gender, birth_date=:birthDate, update_at=:updateAt "
    		+ "WHERE id=:id ")
    void update(@BindBean Customer customer);
    
    @SqlUpdate("DELETE FROM customer "
    		+ "WHERE id=:customerId ")
    void delete(@Bind("customerId") Long customerId);
    
}
