package br.senai.sp.jandira.bmicompose

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicompose.ui.theme.BMIComposeTheme
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import br.senai.sp.jandira.bmicompose.utils.bmiCalculate
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BMICalculator()
                }
            }
        }
    }
}


//
//@Composable
//fun Teste(){
//    Row() {
//
//        for (x in 1 .. 3){
//
//            Button(onClick = { /*TODO*/ }) {
//
//                Text(text = "Botao $x")
//
//            }
//
//        }
//    }
//}
//
//@Preview
//@Composable
//fun TestePreview () {
//
//    Teste()
//
//}

@OptIn(ExperimentalAnimationApi::class)
@Preview(
    showSystemUi = true,
)

@Composable
fun BMICalculator() {

    var weightState by rememberSaveable() {

        mutableStateOf("")

    }

    var heightState by rememberSaveable() {

        mutableStateOf("")

    }

    var expandSate by remember {

        mutableStateOf(false)

    }

    var bmiScoreState by remember {

        mutableStateOf(0.0)

    }

    var isWeightError by remember {

        mutableStateOf(false)

    }
    var isHeightError by remember {

        mutableStateOf(false)

    }

    //********************************
    val weightFocusRequest = FocusRequester()

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {


        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.bmi),
                contentDescription = "imagem Sobre IMC",
                Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = R.string.app_title),
                color = Color.Blue,
                fontSize = 32.sp,
                letterSpacing = 3.sp
            )

        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Column() {

                Text(
                    text = stringResource(id = R.string.weight),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(

                    value = weightState,

                    onValueChange = {

                        var lastChar =
                            if (it.length == 0) it
                            else
                                it.get(it.length - 1)

                        var newValue =
                            if (lastChar == '.' || lastChar == ',') it.dropLast(1) else it
                        weightState = newValue


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequest),
                    leadingIcon = {
                                  
                        Icon(imageVector = Icons.Default.DonutLarge, contentDescription = "icone de uma pessoa")
                                  
                    },
                    trailingIcon = {

                        Icon(imageVector = Icons.Default.Warning, contentDescription = "Icone de perigo")

                    },
                    isError = isWeightError,

                    
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            Column() {

                Text(
                    text = stringResource(id = R.string.height),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(

                    value = heightState,
                    onValueChange = {

                        var lastChar =
                            if (it.length == 0)
                                it
                            else
                                it.get(it.length - 1)

                        var newValue =
                            if (lastChar == '.' || lastChar == ',')
                                it.dropLast(1)
                            else
                                it

                        heightState = newValue
                    },

                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {

                        Icon(imageVector = Icons.Default.Height, contentDescription = "icone de uma pessoa")

                    },
                    trailingIcon = {

                        Icon(imageVector = Icons.Default.Warning, contentDescription = "Icone de perigo")

                    },
                    isError = isHeightError,
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            Button(
                onClick = {

                      isWeightError = weightState.length == 0

                      isHeightError = heightState.length == 0

                    if (isHeightError == false && isWeightError == false){

                        bmiScoreState = bmiCalculate(weightState.toInt(), heightState.toDouble())
                        expandSate = true

                    }


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(40, 150, 30)),
                shape = RoundedCornerShape(16.dp)

            ) {

                Text(text = stringResource(id = R.string.button_calculate))

            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(

            visible = expandSate,
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)

        ) {


            Card(

                Modifier.fillMaxHeight(1f),
                backgroundColor = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

            ) {
                Column(

                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),

                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(

                        text = stringResource(id = R.string.your_score),

                        fontSize = 32.sp,

                    )

                    Text(

                        text = String.format("%.2f", bmiScoreState),
                        fontSize = 32.sp

                    )

                    Text(
                        text = stringResource

                            (id = R.string.Description_BMI),
                        fontSize = 28.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center

                    )
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                        Button(
                            onClick = {

                                expandSate = false
                                weightState = ""
                                heightState = ""
                                weightFocusRequest.requestFocus()

                            },
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        ) {

                            Text(text = stringResource(id = R.string.reset))

                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        ) {

                            Text(text = stringResource(id = R.string.share))

                        }
                    }
                }
            }
        }
    }
}


