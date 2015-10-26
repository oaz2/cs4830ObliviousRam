/*
 *  You can use/modify this class to test your ORAM (for correctness, not security).
 *  
 *  You can experiment modifying this class, but we will not take it into account (we will test your ORAM implementations on this as well as other Jobs)
 *  
 */

package pathOramHw;

import java.util.Arrays;

import pathOramHw.ORAMInterface.Operation;

import java.nio.ByteBuffer;
import java.util.Arrays;

import pathOramHw.ORAMInterface.Operation;

public class Job {

	public static void main(String[] args) {
		int bucket_size = 4;
		int num_blocks = (int) Math.pow(2, 5);
		
		//Set the Bucket size for all the buckets.
		Bucket.setMaxSize(bucket_size);
				
		//Initialize new UntrustedStorage
		UntrustedStorageInterface storage = new ServerStorageForHW();

		//Initialize a randomness generator
		RandForORAMInterface rand_gen = new RandomForORAMHW();
		
		//Initialize a new Oram
		ORAMInterface oram = new ORAMWithReadPathEviction(storage, rand_gen, bucket_size, num_blocks);
		

//		Do same sample computation: fill an array with numbers, then read it back.
		for(int i = 0; i < 60; i++){
			oram.access(Operation.WRITE, i % num_blocks, sampleData(i) );
			System.out.println("Writing Block " + i + " to ORAM. The stash size is: " + oram.getStashSize());
		}
		
		for(int i = 0; i < num_blocks; i++){
			System.out.println("Reading Block " + i + " from ORAM. Value is :" + Arrays.toString(oram.access(Operation.READ, i, null)));
		}
				
	}
	
	private static byte[] sampleData(int i){
		ByteBuffer bb = ByteBuffer.allocate(24); 
		bb.putInt(i); 
		return bb.array();
	}
	
}
