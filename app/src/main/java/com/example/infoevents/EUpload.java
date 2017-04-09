package com.example.infoevents;


import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class EUpload extends Activity{
	//private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString,imgpath,imgname;
    Button select,next;
    Bitmap bitmap;
    ImageView image;
    private static final int PICK_IMAGE = 1;

    //String file_name = uri.getLastPathSegment().toString();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.eimg_upload);	
        select=(Button) findViewById(R.id.select);
        next=(Button) findViewById(R.id.next);
        image = (ImageView) findViewById(R.id.imageView1);
       
        imgpath="null";
        
        select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    selectImageFromGallery();
			}
		});
        next.setOnClickListener(new OnClickListener() {

        	   @Override
        	   public void onClick(View v) {
        	    new ImageUploadTask().execute();
        	   }
        	  });

        
	}
	
	
		public void selectImageFromGallery() {
		  Intent intent = new Intent();
		  intent.setType("image/*");
		  intent.setAction(Intent.ACTION_GET_CONTENT);
		  startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE);
		 }

		 /**
		  * Retrives the result returned from selecting image, by invoking the method
		  * <code>selectImageFromGallery()</code>
		  */
		 @Override
		 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);

		  if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
		    && null != data) {
		   Uri selectedImage = data.getData();
		   String[] filePathColumn = { MediaStore.Images.Media.DATA };

		   Cursor cursor = getContentResolver().query(selectedImage,
		     filePathColumn, null, null, null);
		   cursor.moveToFirst();

		   int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		   String picturePath = cursor.getString(columnIndex);
		   imgpath=picturePath;
		   cursor.close();
		   File f = new File(imgpath);

           imgname = f.getName();
          imgname= imgname.replaceAll(" ", "");

		//   Toast.makeText(EUpload.this, imgname, Toast.LENGTH_LONG).show();
		   decodeFile(picturePath);

		  }
		 }

		 /**
		  * The method decodes the image file to avoid out of memory issues. Sets the
		  * selected image in to the ImageView.
		  * 
		  * @param filePath
		  */
		 public void decodeFile(String filePath) {
		  // Decode image size
		  BitmapFactory.Options o = new BitmapFactory.Options();
		  o.inJustDecodeBounds = true;
		  BitmapFactory.decodeFile(filePath, o);

		  // The new size we want to scale to
		  final int REQUIRED_SIZE = 1024;

		  // Find the correct scale value. It should be the power of 2.
		  int width_tmp = o.outWidth, height_tmp = o.outHeight;
		  int scale = 1;
		  while (true) {
		   if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
		    break;
		   width_tmp /= 2;
		   height_tmp /= 2;
		   scale *= 2;
		  }

		  // Decode with inSampleSize
		  BitmapFactory.Options o2 = new BitmapFactory.Options();
		  o2.inSampleSize = scale;
		  bitmap = BitmapFactory.decodeFile(filePath, o2);

		  image.setImageBitmap(bitmap);
		 }

		 /**
		  * The class connects with server and uploads the photo
		  * 
		  * 
		  */
		 class ImageUploadTask extends AsyncTask<Void, Void, String> {
			 UrlPaths u=new UrlPaths();
		  private String webAddressToPost = u.urladdr()+"/UploadServlet";

		  // private ProgressDialog dialog;
		  private ProgressDialog dialog = new ProgressDialog(EUpload.this);

		  @Override
		  protected void onPreExecute() {
		   dialog.setMessage("Uploading...");
		   dialog.show();
		   
		  }

		  @Override
		  protected String doInBackground(Void... params) {
			  
			  HttpClient httpclient = new DefaultHttpClient();
              httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

              HttpPost httppost = new HttpPost(webAddressToPost);
              File file = new File(imgpath);
              
              MultipartEntity mpEntity = new MultipartEntity();
              ContentBody cbFile = new FileBody(file, "image/jpeg");
              mpEntity.addPart("userfile", cbFile);

              httppost.setEntity(mpEntity);
              Log.e("executing request " + httppost.getRequestLine(), webAddressToPost);
              HttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
				
				return response.toString();
			} 
			/*catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            HttpEntity resEntity = response.getEntity();
           
            if (resEntity != null) {
                   //   Log.e(EntityUtils.toString(resEntity));
            }
            if (resEntity != null) {
             //         resEntity.consumeContent();
            }
            httpclient.getConnectionManager().shutdown();
			  
			
		   return null; */
		   catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return "null";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "null";
				
			}
			catch (Exception e) {
				// TODO: handle exception
				return "null";
			}
			
			
			
		  }

		  @Override
		  protected void onPostExecute(String result) {
		   dialog.dismiss();
		   if(result.equals("null"))
		   {
			   Toast.makeText(getApplicationContext(), "Error!!\nTry again",
					     Toast.LENGTH_LONG).show();
		   }
		   else
		   {
		  // Toast.makeText(getApplicationContext(), "file uploaded",Toast.LENGTH_LONG).show();
		   Intent intent= getIntent();
	        Bundle b = intent.getExtras();

	       String Uemail=(String) b.getString("Uemail");
	        String usernm =(String) b.get("usernm");
		//   Toast.makeText(EUpload.this, usernm, Toast.LENGTH_LONG).show();
		   intent = new Intent(EUpload.this, Event_Upload.class);
		    
		   intent.putExtra("Uemail", Uemail);
		    intent.putExtra("imgnm", imgname);
	        intent.putExtra("usernm", usernm);
		    startActivity(intent);
		   
		   
		   }
		  }

		 }

		}

	
	

