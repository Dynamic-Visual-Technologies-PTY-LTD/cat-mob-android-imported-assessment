package com.glover.template.ui

import android.R.id.home
import androidx.compose.ui.res.painterResource
import com.glover.template.R

/*
Note: All file names need to be in lowercase
run after adding resource in the folder
./gradlew generateComposeResClass

in folder -> [Project]/composeApp/src/commonMain/composeResources/drawable/face.png
Can't find resources add -> Res.drawable.face
run command -> ./gradlew generateComposeResClass
 */
enum class Drawables(val res : Int) {
    Face(R.drawable.ic_launcher_background),
    Home(R.drawable.ic_launcher_background);

    companion object {
        fun to(res: Int): Int {
            /*var output = Create_Folder
            for (action in values()) {
                if (action.value == value) {
                    output = action
                    break
                }
            }*/
            return res
        }
    }

}