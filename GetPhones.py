from fonAPI import FonApi

fon = FonApi('9c010908f0a93b5332ca50f3394c0518efb59fe3d483182b')

Models = ['samsung', 'nokia', 'sony', 'apple', 'lg', 'xiaomi', 'microsoft', 'htc', 'huawei']

phones = fon.getdevice("samsung")
Results = dict()

for Model in Models:
    if not  Model in Results:
        Results[Model] = dict()

    phones = fon.getdevice(Model)
    for phone in phones:
        Name = phone['DeviceName']
        Results[Model][Name] = phone

print Results
