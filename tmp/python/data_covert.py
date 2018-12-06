import os
import pandas as pd
import re

def all_path(dirname, suffix=".csv"):
    filter = [suffix]
    result = []

    for maindir, subdir, file_name_list in os.walk(dirname):

        for filename in file_name_list:
            apath = os.path.join(maindir, filename)
            ext = os.path.splitext(apath)[1]

            if ext in filter:
                result.append(apath)

    return result


source_file = all_path("c:/source/javabigwork/tmp/csv/source_csv")


file_data = 'c:/source/javabigwork/tmp/csv/dest_csv/data.csv'
file_result = 'c:/source/javabigwork/tmp/csv/dest_csv/result.csv'

if(~(os.path.exists(file_data)) or ~(os.path.exists(file_result))):

    drugs = set()
    total = []
    penalty = []
    chinese_time = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十"]

    for file_item in source_file:
        file = pd.read_csv(file_item, engine='python', encoding='utf-8').fillna("n").values
        for row in file:
            if(row[4] == 1 and row[18] != None):
                # print(row)
                drug_item = str(row[18]).split("|")
                # print(drug_item)
                weight = {}
                for item in drug_item:
                    # print(item)
                    detail = item.split("/")

                    if (detail[0] != 'n'):
                        # print(detail[0])
                        drug = re.match(u"[K]?[\u4e00-\u9fa5]+", detail[0]).group()
                        # print(drugs)
                        drugs.add(drug)
                        # print(detail[0])

                        find_float = lambda x: re.search("\d+(\.\d+)?", x).group()
                        # print(find_float(detail[1]))
                        if(drug == "冰毒" or drug == "摇头丸"):
                            drug = "甲基苯丙胺"

                        if(drug == "吗啡"):
                            drug = "鸦片"
                        if weight.get(drug) == None:
                            weight[drug] = float(find_float(detail[1]))
                        else:
                            weight[drug] = float(find_float(detail[1])) + float(weight.get(drug))

                if(weight):
                    sum = 0
                    time = 0
                    # print(file_item)
                    # print(row)
                    for single in row[15]:
                        # print(single)
                        if (single == 'n'):
                            break
                        if (single == "年"):
                            sum += time
                            time = 0
                        elif (single == "月"):
                            sum += time / 12
                            time = 0
                        elif (single == "个"):
                            continue
                        elif(single == ' '):
                            break
                        elif(single == '日' or single == '天'):
                            break
                        elif(single == '零'):
                            break
                        elif(single == '并' or single == '在' or single == '考'):
                            break
                        elif(single == ',' or single == '。'):
                            break
                        elif (single == '的' ):
                            break
                        else:
                            if(single == '两'):
                                single = '二'
                            time = time + chinese_time.index(single) + 1

                    if(str(row[13]).__contains__("容留")):
                        weight["容留"] = 1
                    if (str(row[13]).__contains__("持有")):
                        weight["持有"] = 1
                    if (str(row[13]).__contains__("制造")):
                        weight["制造"] = 1
                    if (str(row[13]).__contains__("伤害")):
                        weight["伤害"] = 1
                    if (str(row[13]).__contains__("运输")):
                        weight["运输"] = 1
                    if (str(row[13]).__contains__("教唆")):
                        weight["教唆"] = 1
                    if (str(row[13]).__contains__("种植")):
                        weight["种植"] = 1
                    penalty.append(sum)
                    total.append(weight)


    data = pd.DataFrame(total)
    result = pd.DataFrame(penalty)

    data = data.fillna(0)
    data.to_csv("c:/source/javabigwork/tmp/csv/dest_csv/data.csv", index=None)
    result.to_csv("c:/source/javabigwork/tmp/csv/dest_csv/result.csv", index=None)