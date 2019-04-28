import random
import time
from datetime import datetime, timedelta

import pymysql
import requests
from randomuser import RandomUser


def insertPolicy(total):
    # Generate a list of 10 random users
    user_list = RandomUser.generate_users(total, {'nat': 'us'})
    for i in range(total):
        serial_no = list('0123456789')
        serial_no = ''.join(serial_no[:8])
        random.shuffle(serial_no)
        place_from = "Beijing" if random.randint(0, 1) else "Dublin"
        place_to = "Dublin" if place_from is "Beijing" else "Beijing"
        policy_type = "Single" if random.randint(0, 1) else "Return"
        validate_from = datetime(2019, random.randint(1, 12), random.randint(1, 15), random.randint(0, 23),
                                 random.randint(0, 59))
        validate_to = validate_from + timedelta(days=random.randint(1, 3))
        customer_id = random.randint(1, 100)
        temp = list('0123456789')
        random.shuffle(temp)
        flight_no = "BD" + ''.join(temp[:6]) if place_from is "Beijing" else "DB" + ''.join(temp[:6])
        policy_holder = user_list[i].get_first_name() + " " + user_list[i].get_last_name()
        insurance_type = "Luggage Lost"
        pieces_of_luggage = random.randint(1, 10)
        print(serial_no, place_from, place_to, validate_from, validate_to, customer_id, flight_no,
              policy_holder, insurance_type, pieces_of_luggage)
        sql = "INSERT INTO policy (serial_no, place_from,place_to,policy_type,validate_from,validate_to,customer_id," \
              "flight_no,policy_holder,insurance_type,pieces_of_luggage) VALUES ('%d', '%s','%s','%s','%s','%s','%d','%s'," \
              "'%s','%s','%d')" % (serial_no, place_from, place_to, policy_type, validate_from,
                                   validate_to, customer_id, flight_no, policy_holder, insurance_type,
                                   pieces_of_luggage)
        cursor.execute(sql)
    connection.commit()
    connection.close()


def insertPolicyAndClaim(total):
    print("insertPolicyAndClaim")
    # Generate a list of total random users
    user_list = RandomUser.generate_users(total, {'nat': 'us'})
    for i in range(total):
        serial_no = list('0123456789')
        random.shuffle(serial_no)
        serial_no = int(''.join(serial_no[:8]))
        place_from = "Beijing" if random.randint(0, 1) else "Dublin"
        place_to = "Dublin" if place_from is "Beijing" else "Beijing"
        policy_type = "Single" if random.randint(0, 1) else "Return"
        validate_from = datetime(random.randint(2010, 2019), random.randint(1, 4), random.randint(1, 25), random.randint(0, 23),
                                 random.randint(0, 59))
        validate_to = validate_from + timedelta(days=random.randint(1, 3))
        customer_id = random.randint(1, 100)
        temp = list('0123456789')
        random.shuffle(temp)
        flight_no = "BD" + ''.join(temp[:6]) if place_from is "Beijing" else "DB" + ''.join(temp[:6])
        policy_holder = user_list[i].get_first_name() + " " + user_list[i].get_last_name()
        insurance_type = "Luggage Lost"
        pieces_of_luggage = random.randint(1, 10)
        print(serial_no, place_from, place_to, validate_from, validate_to, customer_id, flight_no,
              policy_holder, insurance_type, pieces_of_luggage)
        sql = "INSERT INTO policy (serial_no, place_from,place_to,policy_type,validate_from,validate_to,customer_id," \
              "flight_no,policy_holder,insurance_type,pieces_of_luggage) VALUES ('%d', '%s','%s','%s','%s','%s','%d','%s'," \
              "'%s','%s','%d')" % (serial_no, place_from, place_to, policy_type, validate_from,
                                   validate_to, customer_id, flight_no, policy_holder, insurance_type,
                                   pieces_of_luggage)
        cursor.execute(sql)
        print(serial_no, user_list[i].get_street()+" "+user_list[i].get_city(), customer_id, validate_from,
               str(user_list[i].get_picture()), 0, flight_no)
        sql = "INSERT INTO claim (serial_no, billing_address, customer_id, submit_date, details, employee_id, flight_no" \
              ") VALUES ('%d','%s', '%d', '%s', '%s', '%d', '%s')" % \
              (serial_no, user_list[i].get_street()+" "+user_list[i].get_city(), customer_id, validate_from,
               str(user_list[i].get_picture()), 0, flight_no)
        cursor.execute(sql)
    connection.commit()
    connection.close()


def insertUser(total):
    user_list = RandomUser.generate_users(total, {'nat': 'us'})
    headers = {
        "Host": "localhost",
        "Content-Type": "application/x-www-form-urlencoded",
        "Referer": "http://localhost:8080",  # 必须带这个参数，不然会报错
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36",
    }
    url = "http://localhost:8080/result"
    password = "aB12345678"
    for i in range(total):
        form_data = {"password": password, "passwordCheck": password, "emailAddress": user_list[i].get_email(),
                     "lastName": user_list[i].get_last_name(), "firstName": user_list[i].get_first_name(),
                     "passport": user_list[i].get_id_number(), "phoneNumber": user_list[i].get_phone(),
                     "nickname": user_list[i].get_username(), "role": random.randint(1, 2)}
        results = requests.post(url, data=form_data, headers=headers).text


# 连接数据库
connection = pymysql.connect(host='188.131.243.196',
                             port=3306,
                             user='root',
                             passwd='Amazing_5',
                             db='hibernia_sino')
cursor = connection.cursor()

start_time = time.time()
insertPolicyAndClaim(400)
end_time = time.time()
total_time = end_time - start_time
print('插入150条数据花费总时间为：' + str(total_time) + '秒')
