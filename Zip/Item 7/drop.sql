use `Acmeimmigrant`;
revoke all privileges on `Acmeimmigrant`.* from 'acme-user'@'%';
revoke all privileges on `Acmeimmigrant`.* from 'acme-manager'@'%';
drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';