package com.example.tbcexercises.feature_user.presentation.home_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens
import com.example.tbcexercises.feature_user.presentation.model.User

@Composable
fun UserItem(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.SCREEN_TOP)
            .background(Colors.WHITE)
    ) {
        AsyncImage(
            model = user.avatar,
            placeholder = painterResource(R.drawable.person),
            error = painterResource(R.drawable.person),
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Dimens.USER_ITEM_SIZE)
                .clip(RoundedCornerShape(Dimens.ROUNDED_CORNER_LOW))
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = Dimens.USER_ITEM_START),
        ) {
            Text(
                text = user.email, fontSize = Dimens.TEXT_SIZE_MEDIUM, color = Colors.BLACK
            )
            Spacer(modifier = Modifier.height(Dimens.SPACING_LOW))
            Text(
                text = user.fullName, fontSize = Dimens.TEXT_SIZE_MEDIUM, color = Colors.BLACK

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    UserItem(User(1, "sdfkjlsdf", "sdjfklsdf", "sfkldj"))
}