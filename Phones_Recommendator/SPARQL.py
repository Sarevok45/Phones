from django.conf import settings
import rdflib
import rdfextras


rdfextras.registerplugins() # so we can Graph.query()

def ExecuteQuery(Query):
    g = rdflib.Graph()
    g.parse(settings.RDF_PATH)
    return g.query(Query)  # get every predicate and object about the uri