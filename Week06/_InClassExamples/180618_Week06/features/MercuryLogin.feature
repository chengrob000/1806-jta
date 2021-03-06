#Feature is the first keyword of a feature file that simply designates it as such.
Feature: Mercury Tours Login
	I wish to login to Mercury tours using proper credentials.
	
	#Scenario: this keyword designates a single test
	#Scenario Outline must be used in lieu of Scenario if using example tables
	Scenario: Logging into mercury tours
		Given a user is at the login screen for mercury tours.
		When a user shall input a username
		|field		|value		|	type	|
		|userName	|bobbert	|text		|
		|password	|bobbert	|text		|
		|login		|					|click	|
		|fromPort	|Frankfurt|weblist|
		|toMonth	|11				|weblist|
		And a a password and click submit
		Then a user shall be redirected to the find flights page. 
		
	#Examples table must be formatted like below. whitespace doesn't matter
#	Examples: 
#	|username |password	|
#	|bobbert	|bobbert	|
#	|tropicana|tropicana|
#	|goodbye	|hello	  |
	#Example tables provide us the opportunity for data driven frameworks.
	#The test will rerun for each record of data.
	
#OTHER KEYWORDS WORTH NOTING
#AND split a given statement into two versions of it.
#BUT split a given statement into two versions of it.
#Functionally, these two aren't different, the difference lies within
#the interpretation of the gherkin.

#BACKGROUND
#background replaces the scenario tag for creating a test.
#a background scenarios runs once, for each scenario on the feature file.
#It acts like a setup() scenario.