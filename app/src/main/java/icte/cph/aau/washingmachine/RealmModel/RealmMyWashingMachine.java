package icte.cph.aau.washingmachine.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Harjit on 05/05/16.
 */
public class RealmMyWashingMachine extends RealmObject {
    @PrimaryKey
    private String washningMachineID;
    private String washningMachineName;
    private String washingMachineBrand;
    private String UID;

    public String getWashningMachineID() {
        return washningMachineID;
    }

    public void setWashningMachineID(String washningMachineID) {
        this.washningMachineID = washningMachineID;
    }

    public String getWashningMachineName() {
        return washningMachineName;
    }

    public void setWashningMachineName(String washningMachineName) {
        this.washningMachineName = washningMachineName;
    }

    public String getWashingMachineBrand() {
        return washingMachineBrand;
    }

    public void setWashingMachineBrand(String washingMachineBrand) {
        this.washingMachineBrand = washingMachineBrand;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
