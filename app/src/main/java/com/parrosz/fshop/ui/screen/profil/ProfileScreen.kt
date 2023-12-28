package com.parrosz.fshop.ui.screen.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parrosz.fshop.R
import com.parrosz.fshop.ui.theme.FShopTheme

data class UserProfile(
    val name: String,
    val email: String,
    val phoneNumber: String
    // Add other fields as needed
)

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            ProfileHeader(userProfile = userProfile)
        }
        items(profileOptions) { option ->
            ProfileItem(option = option)
        }
    }
}

@Composable
fun ProfileHeader(
    userProfile: UserProfile
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.foto),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(95.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = userProfile.name,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = userProfile.email,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = userProfile.phoneNumber,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ProfileItem(option: ProfileOption) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { /* Handle item click */ }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = option.icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(option.titleResId),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

data class ProfileOption(
    val titleResId: Int,
    val icon: ImageVector
)

val profileOptions = listOf(
    ProfileOption(R.string.profile_settings, Icons.Default.Settings),
    // Add more options as needed
)

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    FShopTheme {
        ProfileScreen(
            userProfile = UserProfile(
                name = "John Doe",
                email = "john.doe@example.com",
                phoneNumber = "+1234567890"
            )
        )
    }
}
