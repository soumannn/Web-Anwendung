zeilen = open("uebersetzungen.csv",'r').read().splitlines()

sprachen = [sp for sp in zeilen[0].split(';')][1:]

anzahl = len(sprachen)
print(sprachen)

properties ={}

for zeile in zeilen[1:]:
    if(zeile.startswith('#') or not zeile.strip()):
        continue
    prop_name, *uebersetzungen = zeile.split(';')
    properties[prop_name] = uebersetzungen
    
print(properties)   
i=0
for sprache in sprachen:
    prop_file = open(f'src/main/resources/messages_{sprache}.properties', 'w', encoding='utf-8')
    if(i==0):
        default_file= open(f'src/main/resources/messages.properties', 'w', encoding='utf-8')
    for prop_name, uebersetzungen in properties.items():
        if i==0:
            default_file.write(f"{prop_name}={uebersetzungen[i]}\n")
        prop_file.write(f"{prop_name}={uebersetzungen[i]}\n")
    i= i+1