declare
    v_supervisor_number number;
begin
     select role_number
    into v_supervisor_number
    from project_1_role
    where role_name='Supervisor';
    dbms_output.put_line('The role number for Supervisor is ' || v_supervisor_number);
 end;
 
select 
       u.username, u.first_name, u.last_name,
       u.email, u.phone_number, areimbursement.amount_available,
       f.form_uuid, f.employee_uuid, f.form_creation_date,
       f.status, f.direct_supervisor_uuid, f.department_head_uuid,
       f.benco_decision, f.supervisor_decision, f.supervisor_decision_date,
       f.supervisor_comments, f.depertment_head_decision, f.department_head_decision_date,
       rform.form_closed_date, rform.present_to_management_required, rform.completed_with_satisfaction
       from project_1_reimbursement_form f
       left outer join project_1_user u
       on rform.employee_uuid = u.uuid
       left outer join project_1_user_role_relationship rrelationship
       on rform.employee_uuid = rrelationship.employee_uuid
       left outer join project_1_available_reimbursement areimbursement
       on areimbursement.employee_uuid = u.uuid
       left outer join project_1_attachment attachment
       on attachment.employee_uuid = u.uuid
       left outer join project_1_grade grade
       on grade.form_uuid = u.uuid;
/
 
select * from project_1_user;

select * from project_1_role;

select * from project_1_role where role_name='Supervisor';

call selectUserByUsername('user');