import os

import pandas as pd
import numpy as np
import sys
import json

from sklearn.externals import joblib
from sklearn.linear_model import LinearRegression



def main(a):

   data = pd.read_csv("c:/source/javabigwork/tmp/csv/dest_csv/data.csv")

   train_data = data.values
   header = list(data.columns)
   # print(header)

   train_result = pd.read_csv("c:/source/javabigwork/tmp/csv/dest_csv/result.csv").values

   # print(a)

   # test = '{"cyclohexanone":20.0,"heroin":30.0,"methamphetamine":100.0,"marijuana":120.0,"hurt":1.0,"produce":1.0,"accept":1.0,"hold":1.0,"transport":1.0}'
   translate = {"cyclohexanone": "K粉", "heroin": "海洛因", "methamphetamine": "甲基苯丙胺", "marijuana": "大麻",
                "hurt": "伤害", "produce": "制造", "accept": "容留", "hold": "持有", "transport": "运输", "opium": "鸦片", "caffeine": "咖啡因"}
   parse = json.loads(a)

   test_data = [0 for i in range(header.__len__())]

   for item in parse:
       # print(item)
       # print(parse.get(item))
       test_data[header.index(translate[item])] = parse.get(item)
   # print(test_data)

   file = 'c:/source/javabigwork/tmp/csv/dest_csv/modelFile.pkl'

   if os.path.exists(file):
       linreg = joblib.load(file)
       print(linreg.predict(np.mat(test_data))[0][0])
   else:
       linreg = LinearRegression(normalize=True)
       linreg.fit(train_data, train_result)

        # print(linreg.intercept_)
        # print(linreg.coef_)

       print(linreg.predict(np.mat(test_data))[0][0])
       joblib.dump(linreg, file)


if __name__ == '__main__':
    for i in range(1, len(sys.argv)):
        url = sys.argv[i]
        main(url)