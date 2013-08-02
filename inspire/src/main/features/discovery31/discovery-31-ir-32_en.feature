Feature: Requirement 32
  If a client sends an invalid CSW Discovery.GetRecords request (that is, 
  not compliant to CSW ISO AP) containing a language specific filter and 
  this causes an exception at the service, the exception shall be responded 
  in the default or in a requested and supported language. The use of a valid 
  language specific filter itself shall not raise an exception, even if the 
  requested language is not supported.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).
