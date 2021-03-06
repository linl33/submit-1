package org.opendatakit.submit.address;

import java.util.HashMap;

import org.opendatakit.submit.exceptions.InvalidAddressException;
import org.opendatakit.submit.flags.HttpFlags;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * HttpAddress
 * @author mvigil
 *
 */
public class HttpAddress extends DestinationAddress implements Parcelable {
	// For multipart HttpEntity
	private HashMap<String,String> mParams = null;
	private HttpFlags mFlag = HttpFlags.PUT;

	/**
	 * HttpAddress constructor
	 * @param dest
	 * @throws InvalidAddressException
	 */
	public HttpAddress(String dest, HttpFlags direction) throws InvalidAddressException {
		super();
		//if(URLUtil.isValidUrl(dest)) {
			super.setAddress(dest);
			mParams = new HashMap<String,String>();
			mFlag = direction;
		//} else {
		//	throw new InvalidAddressException("Invalid URL.");
		//}
	}

	/**
	 * Add a parameter for multi-part Http Requests
	 * @param param
	 * @param value
	 */
	public void addHeader(String param, String value) {
		mParams.put(param, value);
	}

	/**
	 * Get the HashMap of all the parans
	 * for multi-part Http Requests
	 * @return
	 */
	public HashMap<String,String> getHeaders() {
		return mParams;
	}

	/**
	 * HttpAddress constructor from Parcel
	 * @param in
	 * @throws InvalidAddressException
	 */
	public HttpAddress(Parcel in) throws InvalidAddressException {
		super();
		readFromParcel(in);
	}

	public HttpFlags getHttpFlag() {
		return mFlag;
	}

	@Override
	public String getAddress() {
		return super.getAddress();
	}

	@Override
	public void setAddress(String dest) throws InvalidAddressException {
		super.setAddress(dest);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// HttpAddress Address
		dest.writeString(getAddress());
		// HttpFlags
		dest.writeString(mFlag.toString());
		dest.writeMap(mParams);
	}

	public void readFromParcel(Parcel in) {
		try {
			// read address and instantiate

			// Read HttpAddress
			setAddress(in.readString());
			// Read HttpFlags
			mFlag = HttpFlags.valueOf(in.readString());
			mParams = in.readHashMap(HashMap.class.getClassLoader());
		} catch (InvalidAddressException e) {
			Log.e(HttpAddress.class.getName(), e.getMessage());
			e.printStackTrace();
		}
	}

	public static final Parcelable.Creator<HttpAddress> CREATOR =
		    new Parcelable.Creator<HttpAddress>() {
		        public HttpAddress createFromParcel(Parcel in) {
		            try {
						return new HttpAddress(in);
					} catch (InvalidAddressException e) {
						Log.e(HttpAddress.class.getName(), e.getMessage());
						e.printStackTrace();
					}
		            return null;
		        }

		        public HttpAddress[] newArray(int size) {
		            return new HttpAddress[size];
		        }
	};




}
