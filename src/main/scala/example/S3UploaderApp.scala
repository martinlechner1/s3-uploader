package example

import java.util.concurrent.Executors

import cats.effect._
import cats.implicits._
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.{
  PutObjectRequest,
  PutObjectResponse
}

import scala.concurrent.ExecutionContext

object S3UploaderApp extends IOApp {

  val parallelism = 10

  implicit val executionContext: ExecutionContext =
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(parallelism))

  val client: S3AsyncClient = S3AsyncClient.create

  val files = List(MyFile("a", "Hi", "mlechner-test-bucket"),
                   MyFile("b", "Hib", "mlechner-test-bucket"))

  def s3upload(file: MyFile): IO[PutObjectResponse] =
    Util
      .fromJavaFuture(
        client.putObject(PutObjectRequest
                           .builder()
                           .bucket(file.bucket)
                           .key(file.key)
                           .build(),
                         AsyncRequestBody.fromString(file.body)))

  def run(args: List[String]): IO[ExitCode] =
    files
      .parTraverse(s3upload)
      .map(l => println(s"Uploaded ${l.size} files to S3"))
      .as(ExitCode.Success)

}
