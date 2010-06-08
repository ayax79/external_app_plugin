package com.syncapse.scalaplug

import org.specs.{SpecificationWithJUnit}
import org.specs.mock.Mockito
import com.jivesoftware.base.{UserManager, User}

class ScalaPlugTest extends SpecificationWithJUnit with Mockito {
  val userManager = mock[UserManager]
  val user = mock[User]

  userManager.getUser(1) returns user
  user.getName returns "admin"

  val scalaPlugin = new ScalaPlugin()
  scalaPlugin.userManager = userManager


  "The init method" should {
    scalaPlugin.init


    "should get the admin user" in {
      userManager.getUser(1) was called
    }

    "should call the user's name method" in {
      user.getName was called
    }

  }


}