## emergency100
## MEDICAL-IDENTITY_CARD
LOCKSCREEN used for showing medical info .<br />
Broadcast receiver is used for sending coordinates to emergency contacts. <br />
PERMISSIONS:<br />
 1. Location permission for capturing the coordinates.<br />
 2. SMS permission for sending the coordinates<br />

![Screenshot 2021-08-10 183051](https://user-images.githubusercontent.com/75937169/128874875-cf3201c4-96c7-438f-b459-440a43a53007.png)
![Screenshot 2021-08-10 183952](https://user-images.githubusercontent.com/75937169/128874967-18bfb14c-564d-4a2d-aeaa-5c551467cc4c.png)
![Screenshot 2021-08-10 184056](https://user-images.githubusercontent.com/75937169/128874975-2dabd027-2ec4-4a47-a89d-6db6b6867df1.png)
![Screenshot 2021-08-10 184225](https://user-images.githubusercontent.com/75937169/128874984-9c41bd94-8866-4e04-952b-81660588914c.png)

for enabling the location service , all permissions must be accepted by clicking the button  "LOCATION SERVICE" on HOME screen of app (On first Startup).<br />
Tested on API level 27 .<br />
App to help people for fast-immediate treatment during loss of consciousness, head injury, accidents. <br />
Users can save their info about medical conditions / Previous surgeries / allergies & illness /ongoing medications. <br />
during health incidents, this app helps the user to get proper assistance from people around by showing IMPORTENT info like blood group ,previous medical conditions ,illness ,etc on the LOCK SCREEN. <br />
The device will always show CUSTOM LOCKSCREEN NOTIFICATION including all crucial information , Emergency contacts when device is locked. <br />

![Screenshot 2021-08-10 020125](https://user-images.githubusercontent.com/75937169/128770542-8500ea1a-6ae8-4d1c-a087-5e0540408ff3.png)

App saves all the data in SQLite database .<br />
All queries are managed using contentProviders ,LoaderManager.<br />
No additional permissions are required for the app to run. <br />



![Screenshot 2021-08-10 015937](https://user-images.githubusercontent.com/75937169/128770696-078e1d84-6f89-4779-a1a9-6b80e60dcec0.png)
![Screenshot 2021-08-10 015915](https://user-images.githubusercontent.com/75937169/128770701-cf59be7b-c2b0-431c-8036-d4a62f7cf51a.png)
